package com.cs467.helldivers2_stratagemheromobile

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
    private var dewIt = false
    private var addFinalScore = false
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
                //playTransition(R.raw.start)
            }
            composable(route = "ready_screen") {
                //if (dewIt) {
                //dewIt = true
                //playTransition(R.raw.sound_round_start)
                //    dewIt = false
                //}
                ReadyScreen(
                    roundNumber = viewModel.round,
                    navController = navController
                )
            }
            composable(route = "gameplay_screen") {
                dewIt = true
                addFinalScore = true
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
                playTransition(R.raw.sound_gameover)
                if(addFinalScore == true){
                    highScoreDao.insertScore(HighScoreEntity(playerName = giveRandName(), playerScore = viewModel.score))
                    addFinalScore = false
                }

                viewModel.gameOverRoundReset()

                GameOverScreen(
                    "Game over",
                    //threeTopScores = listOf<Score>(Score("Player 1", 99990),Score("Player 2" , 99500), Score("Player 3", 95500)),
                    threeTopScores = highScoreDao.getTopScores(),
                    //Update this to the viewModel Final Score (all the rounds scores added together)
                    finalScore = HighScoreEntity(playerName = "John Helldiver", playerScore = viewModel.score),
                    modifier = Modifier,
                    navController = navController,
                    mainViewModel = viewModel
                )
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        //Test for background music
        //var mediaPlaya = MediaPlayer.create(this, R.raw.bg1_music)
        //mediaPlaya.start()
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

        // Observe the ViewModel's LiveData
        viewModel.playSoundEvent.observe(this, Observer { shouldPlay ->
            if (shouldPlay) {
                playSound(viewModel.soundToPlay)
                viewModel.onSoundTriggered(false)  // Reset the event
            }
        })
    }

    private fun playSound(beat: Int) {
        mediaPlayer = MediaPlayer.create(this, beat) // Add a sound file in 'res/raw'
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.reset()
            it.release() // Release the MediaPlayer when the sound is done
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Make sure to release the MediaPlayer when the Activity is destroyed
    }

    private fun playBackgroundMusic() {
        // Check if the MediaPlayer is already playing
        if (bgMusicPlayer == null) {
            // Create the MediaPlayer instance, pointing to your music file in res/raw
            bgMusicPlayer = MediaPlayer.create(this, R.raw.round_music)
            // Set the looping property to true for continuous playback
            bgMusicPlayer?.isLooping = true
            // Start playing the music
            bgMusicPlayer?.setVolume(0F, 0.5F)
            bgMusicPlayer?.start()
        }
    }

    private fun playTransition(riff: Int) {
        if (dewIt) {
            // Create the MediaPlayer instance, pointing to your music file in res/raw
            transitionMusicPlayer = MediaPlayer.create(this, riff)
            // Start playing the music
            transitionMusicPlayer?.start()
            transitionMusicPlayer?.setOnCompletionListener {
                it.reset()
                it.release() // Release the MediaPlayer when the sound is done
            }
            dewIt = false
        }
    }

    override fun onPause() {
        super.onPause()
        // Pause the music when the activity goes into the background
        bgMusicPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        // Resume the music when the activity comes back to the foreground
        bgMusicPlayer?.start()
    }

    override fun onStop() {
        super.onStop()
        // Release the MediaPlayer to avoid memory leaks
        //bgMusicPlayer?.reset()
        bgMusicPlayer?.release()
        bgMusicPlayer = null
    }

    private fun giveRandName(): String {
        val randomNumber = Random.nextInt(0, 999)
        val newname = "Helldiver_$randomNumber"
        return newname
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


