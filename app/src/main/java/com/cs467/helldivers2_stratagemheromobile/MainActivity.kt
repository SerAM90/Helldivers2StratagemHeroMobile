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

                    }
                }
            }
        }
    }
