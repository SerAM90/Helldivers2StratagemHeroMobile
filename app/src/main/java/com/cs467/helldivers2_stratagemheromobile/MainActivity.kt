package com.cs467.helldivers2_stratagemheromobile

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
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
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs467.helldivers2_stratagemheromobile.Screens.AfterRoundScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.GameOverScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.GameplayScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.ReadyScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.Score
import com.cs467.helldivers2_stratagemheromobile.Screens.StartingScreen
import com.cs467.helldivers2_stratagemheromobile.model.StratagemInput
import com.cs467.helldivers2_stratagemheromobile.ui.theme.Helldivers2StratagemHeroMobileTheme
import kotlin.math.abs

class MainActivity : ComponentActivity() {

    private var x0 = 0.0f
    private var y0 = 0.0f
    private val viewModel by viewModels<MainViewModel>()

    companion object {
        private val DEBUG_TAG: String = MainActivity::class.java.simpleName
        const val SWIPE_THRESHOLD = 150
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        
        NavHost(navController = navController, startDestination = "starting_screen") {
            composable(route = "starting_screen") {
                StartingScreen(
                    navController = navController
                )
            }
            composable(route = "ready_screen") {
                ReadyScreen(
                    roundNumber = viewModel.round,
                    navController = navController
                )
            }
            composable(route = "gameplay_screen") {
                viewModel.isPlaying = true
                viewModel.pickStratagems()
                GameplayScreen(
                    mainViewModel = viewModel,
                    navController = navController
                )
            }
            composable(route = "after_round_screen"){
                viewModel.isPlaying = false
                viewModel.resetForNewRound()
                AfterRoundScreen(
                    roundBonus = 0,
                    timeBonus = 0,
                    perfectBonus = 0,
                    totalScore = viewModel.score,
                    modifier = Modifier,
                    navController = navController,
                    mainViewModel = viewModel
                )
            }

            composable(route = "game_over_screen"){
                viewModel.isPlaying = false
                GameOverScreen(
                    "Game over",
                    threeTopScores = listOf<Score>(Score("Player 1", 99990),Score("Player 2" , 99500), Score("Player 3", 95500)),
                    //Update this to the viewModel Final Score (all the rounds scores added together)
                    finalScore = Score("Current Player", viewModel.score),
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
                        /*
                        GameScreen(
                            game round initialization
                                initialize timer to starting time (~20 sec)
                                initialize total score to 0

                            execute function to constantly decrement timer
                            set rounds completed to 0

                            while timer > 0
                                randomly select stratagems equal in number to rounds completed + 6
                                    add each one to a list
                                set # of stratagems left equal to round strat count
                                set round score to 0
                                set perfect count to 0
                                while # of stratagems left > 0
                                    select and display next stratagem from list
                                    set arrows complete to 0
                                    while stratagem incomplete
                                        if user input matches current arrow
                                            move to next arrow
                                            increment arrows complete by 1
                                        else
                                            display incorrect
                                            restart stratagem sequence
                                    increment timer by 2 seconds
                                    multiply arrows complete by 5 & add to round score
                                    if no mistakes in inputs for last stratagem
                                        increment perfect count by 1
                                    decrement stratagems left by 1

                                if perfect count = rounds completed + 6
                                    add 100 to round score
                                calculate percent of timer left at round end
                                    multiply by 100 and add to round score
                                add 75 + rounds completed to round score
                                add round score to total score
                                increment rounds completed by 1

                            display game over screen
                                display total score
                                display rounds completed
                                give player option to enter name for high score

                            add player name, high score as tuple into high score database
                                insert at correct position in list of scores sorted by descending order
                        )
                         */

                }
            }

        }
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


