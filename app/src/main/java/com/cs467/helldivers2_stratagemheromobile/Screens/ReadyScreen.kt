package com.cs467.helldivers2_stratagemheromobile.Screens

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs467.helldivers2_stratagemheromobile.MainViewModel
import com.cs467.helldivers2_stratagemheromobile.R
import kotlinx.coroutines.delay

//GET READY
//Round
//1 (round number)
//Some kind of phrase at the bottom of the screen?****************************************
@Composable
        /**
         * The function below is for the ReadyScreen. This screen is displayed before each round. The screen shows the 'GET READY', and current round that the user is about to start.
         */
fun ReadyScreen(roundNumber: Int, navController: NavController)
{
    val context = LocalContext.current
    // This creates a 2s delay from the ready screen to the gameplay screen
    LaunchedEffect(true) {
        delay(1000)
        val mediaPlayer = MediaPlayer.create(context, R.raw.sound_round_start) // Add a sound file in 'res/raw'
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.reset()
            it.release() // Release the MediaPlayer when the sound is done
        }
        delay(2000)
        navController.navigate("gameplay_screen")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.get_ready),
                fontSize = 50.sp
            )

            Text(
                text = stringResource(id = R.string.round),
                fontSize = 40.sp
            )
            Text(
                text = roundNumber.toString(),
                fontSize = 30.sp
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 360)
@Composable
fun ReadyScreenPreview(){
ReadyScreen(
roundNumber = 1, rememberNavController())
}