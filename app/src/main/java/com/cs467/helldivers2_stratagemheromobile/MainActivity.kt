package com.cs467.helldivers2_stratagemheromobile

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cs467.helldivers2_stratagemheromobile.Screens.AfterRoundScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.GameOverScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.GameplayScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.ReadyScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.Score
import com.cs467.helldivers2_stratagemheromobile.Screens.StartingScreen
//import com.cs467.helldivers2_stratagemheromobile.Screens.giveRandName
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreDao
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreDatabase
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreEntity
import com.cs467.helldivers2_stratagemheromobile.model.StratagemInput
import com.cs467.helldivers2_stratagemheromobile.ui.theme.Helldivers2StratagemHeroMobileTheme
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private var x0 = 0.0f
    private var y0 = 0.0f
    private val viewModel by viewModels<MainViewModel>()
    private var mediaPlayer: MediaPlayer? = null
    private var bgMusicPlayer: MediaPlayer? = null
    private var transitionMusicPlayer: MediaPlayer? = null
    private var soundPlayable = false
    private var gameInitiated = false
    private var startAgain = false
    private lateinit var db: HighScoreDatabase
    private lateinit var highScoreDao: HighScoreDao


    companion object {
        private val DEBUG_TAG: String = MainActivity::class.java.simpleName
        const val SWIPE_THRESHOLD = 150
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        
        NavHost(navController = navController, startDestination = "starting_screen") {
            composable(route = "starting_screen") {
                viewModel.score = 0
                StartingScreen(
                    navController = navController
                )
                gameInitiated = true
            }
            composable(route = "ready_screen") {
                ReadyScreen(
                    roundNumber = viewModel.round,
                    navController = navController
                )
                startAgain = false
            }
            composable(route = "gameplay_screen") {
                soundPlayable = true
                if (bgMusicPlayer != null) {
                    onResume()
                }
                else
                    playBackgroundMusic()
                viewModel.isPlaying = true
                viewModel.pickStratagems()
                GameplayScreen(
                    mainViewModel = viewModel,
                    navController = navController
                )
            }
            composable(
                route = "after_round_screen?roundBonus={roundBonus}&timeBonus={timeBonus}&perfectBonus={perfectBonus}",
                arguments = listOf(navArgument("roundBonus") { type = NavType.IntType },
                    navArgument("timeBonus") { type = NavType.IntType },
                    navArgument("perfectBonus") {type = NavType.IntType}
                )
            ) {
                val roundBonus = it.arguments?.getInt("roundBonus") ?: 0
                val timeBonus = it.arguments?.getInt("timeBonus") ?: 0
                val perfectBonus = it.arguments?.getInt("perfectBonus") ?: 0

                viewModel.isPlaying = false
                viewModel.resetForNewRound()

                onPause()

                //viewModel.score = 0 //reset score to 0

                AfterRoundScreen(
                    roundBonus = roundBonus,
                    timeBonus = timeBonus,
                    perfectBonus = perfectBonus,
                    totalScore = viewModel.score,
                    modifier = Modifier,
                    navController = navController,
                    mainViewModel = viewModel
                )
            }

            composable(route = "game_over_screen"){

                viewModel.isPlaying = false
                onStop()

                //Only enact once if screen refreshes
                if(gameInitiated && !startAgain){
                    playTransition(R.raw.sound_gameover)
                    //No name input popup if no score
                    if(viewModel.score > 0) {
                        val rootView: View = findViewById(android.R.id.content)
                        showEnterNameSnackbar(rootView)
                    }
                    gameInitiated = false
                    startAgain = true
                }

                viewModel.gameOverRoundReset()

                GameOverScreen(
                    "Game over",
                    //threeTopScores = listOf<Score>(Score("Player 1", 99990),Score("Player 2" , 99500), Score("Player 3", 95500)),
                    threeTopScores = highScoreDao.getTopScores(),
                    //Update this to the viewModel Final Score (all the rounds scores added together)
                    finalScore = HighScoreEntity(playerName = "Your Score", playerScore = viewModel.score),
                    modifier = Modifier,
                    navController = navController,
                    mainViewModel = viewModel
                )
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = HighScoreDatabase.getDatabase(this)
        highScoreDao = db.hsDao()
        // Insert sample data and query in a coroutine
//        lifecycleScope.launch {
//            // Insert sample data
//            try {
//                highScoreDao.insertScore(HighScoreEntity(playerName = "Sam", playerScore = 500))
//                highScoreDao.insertScore(HighScoreEntity(playerName = "Bob", playerScore = 42))
//                highScoreDao.insertScore(HighScoreEntity(playerName = "Monk", playerScore = 8989))
//            } catch (e: Exception) {
//                Log.e("DatabaseError", "Error inserting data: ${e.message}")
//            }
//        }
        // Hide the status bar and action bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        setContent {
            Helldivers2StratagemHeroMobileTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInteropFilter { event ->
                            when (event.action) {
                                // This runs when the user presses down
                                MotionEvent.ACTION_DOWN -> {
                                    if (viewModel.isPlaying) {
                                        x0 = event.x
                                        y0 = event.y
                                        true
                                    } else false
                                }
                                // This runs when the user lets go of the press
                                MotionEvent.ACTION_UP -> {
                                    if (viewModel.isPlaying) {
                                        val deltaX = event.x - x0
                                        val deltaY = event.y - y0
                                        onSwipeEnd(deltaX, deltaY)
                                        true
                                    } else false
                                }

                                else -> false
                            }
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }

        }

        //Observe the ViewModel's LiveData to listen for action to play corresponding sound
        viewModel.playSoundEvent.observe(this, Observer { shouldPlay ->
            if (shouldPlay) {
                playSound(viewModel.soundToPlay)
                viewModel.onSoundTriggered(false)  // Reset the event
            }
        })
    }

    private fun playSound(beat: Int) {
        //Create MediaPlayer instance and play appropriate sound depending on user input
        mediaPlayer = MediaPlayer.create(this, beat)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.reset()
            it.release()
        }
    }

    override fun onDestroy() {
        //Ensure no leakage with proper MediaPlayer object destruction
        super.onDestroy()
        mediaPlayer?.release()
    }

    private fun playTransition(riff: Int) {
        if (soundPlayable) {
            transitionMusicPlayer = MediaPlayer.create(this, riff)
            transitionMusicPlayer?.start()
            transitionMusicPlayer?.setOnCompletionListener {
                it.reset()
                it.release()
            }
            soundPlayable = false
        }
    }

    private fun playBackgroundMusic() {
        //Check if the MediaPlayer is already playing
        if (bgMusicPlayer == null) {
            bgMusicPlayer = MediaPlayer.create(this, R.raw.round_music)
            bgMusicPlayer?.isLooping = true
            bgMusicPlayer?.setVolume(0F, 0.5F)
            bgMusicPlayer?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        //Pause the background music
        bgMusicPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        //Resume the background music
        bgMusicPlayer?.start()
    }

    override fun onStop() {
        //Stop and release the MediaPlayer to avoid memory leaks
        super.onStop()
        //bgMusicPlayer?.reset()
        bgMusicPlayer?.release()
        bgMusicPlayer = null
    }

//    private fun giveRandName(): String {
//        val randomNumber = Random.nextInt(0, 999)
//        val newname = "Helldiver_$randomNumber"
//        return newname
//    }

    private fun showEnterNameSnackbar(view: View) {
        //Create Snackbar instance and automatically open dialog for name input
        Snackbar.make(view, "In the name of Liberty - Great Job Helldiver!", Snackbar.LENGTH_LONG).show()
        showNameInputDialog()
    }

    private fun showNameInputDialog() {
        //Pop-up window allowing user to optionally enter name
        val editText = EditText(this)
        editText.hint = "Enter your name here"

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Your Score")
            .setMessage("Please enter your name to save your high score:")
            .setView(editText)
            //.setCancelable(false)
            .setPositiveButton("SUBMIT") { _, _ ->
                val name = editText.text.toString()
                if (name.isNotBlank()) {
                    saveNameAndScore(name)
                } else {
                    Toast.makeText(this, "Name cannot be empty.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("CANCEL", null)
            .show()
            //.create()
        //dialog.show()
    }

    private fun saveNameAndScore(name: String) {
        //Called via Snackbar instance if name provided to insert new HS Entity
        highScoreDao.insertScore(
            HighScoreEntity(
                playerName = name,
                playerScore = viewModel.score
            )
        )
        Toast.makeText(this, "$name's score has been added!", Toast.LENGTH_SHORT).show()
    }

    /**
     * This function determines the direction of the swipe touch event and calls the appropriate
     * swipe direction function (in horizontal mode only)
     * @param deltaX Change in x position
     * @param deltaY Change in y position
     */
    private fun onSwipeEnd(deltaX: Float, deltaY: Float) {
        if (abs(deltaX) > SWIPE_THRESHOLD) {
            if (deltaX > 0) {
                onSwipeRight()
            } else {
                onSwipeLeft()
            }
        } else if (abs(deltaY) > SWIPE_THRESHOLD) {
            if (deltaY < 0) {
                onSwipeUp()
            } else {
                onSwipeDown()
            }
        }
    }

    private fun onSwipeRight() {
        Log.d(DEBUG_TAG, "swipe right")
        viewModel.checkSwipe(StratagemInput.RIGHT)
    }

    private fun onSwipeLeft() {
        Log.d(DEBUG_TAG, "swipe left")
        viewModel.checkSwipe(StratagemInput.LEFT)
    }

    private fun onSwipeUp() {
        Log.d(DEBUG_TAG, "swipe up")
        viewModel.checkSwipe(StratagemInput.UP)
    }

    private fun onSwipeDown() {
        Log.d(DEBUG_TAG, "swipe down")
        viewModel.checkSwipe(StratagemInput.DOWN)
    }
    
}


