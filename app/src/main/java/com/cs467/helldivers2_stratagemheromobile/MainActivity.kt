package com.cs467.helldivers2_stratagemheromobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cs467.helldivers2_stratagemheromobile.Screens.GameOverScreen
import com.cs467.helldivers2_stratagemheromobile.ui.theme.Helldivers2StratagemHeroMobileTheme
import com.cs467.helldivers2_stratagemheromobile.Screens.ReadyScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.StartingScreen
import com.cs467.helldivers2_stratagemheromobile.Screens.GameOverScreen

class MainActivity : ComponentActivity() {
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
        }
    }
