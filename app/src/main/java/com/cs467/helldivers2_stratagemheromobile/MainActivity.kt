package com.cs467.helldivers2_stratagemheromobile

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import com.cs467.helldivers2_stratagemheromobile.ui.theme.Helldivers2StratagemHeroMobileTheme
import com.cs467.helldivers2_stratagemheromobile.Screens.StartingScreen
import kotlin.math.abs

class MainActivity : ComponentActivity() {

    private var x0 = 0.0f
    private var y0 = 0.0f

    companion object {
        private val DEBUG_TAG: String = MainActivity::class.java.simpleName
        const val SWIPE_THRESHOLD = 150
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Helldivers2StratagemHeroMobileTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        // TODO: we need to separate this behavior depending on the state (only run
                        //  when we are in the game state)
                        .pointerInteropFilter {
                            when (it.action) {
                                // This runs when the user presses down
                                MotionEvent.ACTION_DOWN -> {
                                    x0 = it.x
                                    y0 = it.y
                                }
                                // This runs when the user lets go of the press
                                MotionEvent.ACTION_UP -> {
                                    var deltaX = it.x - x0
                                    var deltaY = it.y - y0
                                    onSwipeEnd(deltaX, deltaY)
                                }
                                // This means: do not handle any other touch events
                                else -> false
                            }
                            true
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                        /*
                        ReadyScreen(
                            readyDisplay = getString(R.string.get_ready),
                            round = getString(R.string.round),
                            roundNumber = 1,
                            modifier = Modifier
                        )
                        */

                        StartingScreen(
                            title = stringResource(id = R.string.title_start_screen),
                            instructions = stringResource(id = R.string.start_screen_instructions),
                            modifier = Modifier
                        )

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
     * swipe direction function
     * @param deltaX Change in x position
     * @param deltaY Change in y position
     */
    // TODO: Depending on whether it is in horizontal or vertical mode, the swipe directions change
    //  i.e., swipe right in vertical becomes swipe up in horizontal. Once we figure out how to
    //  incorporate state and the view model we can revisit this function and call the right swipe
    //  method from here
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
        // TODO("Not yet implemented")
    }

    private fun onSwipeLeft() {
        Log.d(DEBUG_TAG, "swipe left")
        // TODO("Not yet implemented")
    }

    private fun onSwipeUp() {
        Log.d(DEBUG_TAG, "swipe up")
        // TODO("Not yet implemented")
    }

    private fun onSwipeDown() {
        Log.d(DEBUG_TAG, "swipe down")
        // TODO("Not yet implemented")
    }
    
}


