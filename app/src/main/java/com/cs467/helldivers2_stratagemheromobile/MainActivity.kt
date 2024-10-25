package com.cs467.helldivers2_stratagemheromobile

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cs467.helldivers2_stratagemheromobile.ui.theme.Helldivers2StratagemHeroMobileTheme
import com.cs467.helldivers2_stratagemheromobile.Screens.StartingScreen

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var gestureDetector: GestureDetector
    var x1 = 0.0f
    var x2 = 0.0f
    var y1 = 0.0f
    var y2 = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Helldivers2StratagemHeroMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

//                        ReadyScreen(
//                            readyDisplay = getString(R.string.get_ready),
//                            round = getString(R.string.round),
//                            roundNumber = 1,
//                            modifier = Modifier
//                        )
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
        Log.d(DEBUG_TAG, "testing logger")
        gestureDetector = GestureDetector(this, this)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            gestureDetector.onTouchEvent(event)
        }

        when (event?.action) {
            // Start swiping motion
            0 -> {
                x1 = event.x
                y1 = event.y

            }
            // End swipe motion
            1 -> {
                x2 = event.x
                y2 = event.y
                val deltaX = x2-x1
                val deltaY = y2-y1
                // capture the slope and then decide the direction of the swipe
                Log.d(DEBUG_TAG, deltaX.toString())
                Log.d(DEBUG_TAG, deltaY.toString())
            }
        }

        return super.onTouchEvent(event)
    }

    companion object {
        private val DEBUG_TAG: String = MainActivity::class.java.simpleName
    }

    override fun onDown(p0: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDown: ");
        return true
    }

    override fun onShowPress(p0: MotionEvent) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
        //TODO("Not yet implemented")
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }
}


