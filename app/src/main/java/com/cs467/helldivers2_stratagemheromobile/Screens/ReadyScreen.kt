package com.cs467.helldivers2_stratagemheromobile.Screens

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

fun ReadyScreen(roundNumber: Int, navController: NavController) {
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
        navController.navigate("gameplay_screen") //nav is on a delay
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(R.drawable.superearthbackground),
            contentDescription = "Super Earth Logo",
            modifier = Modifier
                .padding(top = 3.dp)
                .fillMaxHeight(0.6f)
                .fillMaxWidth(),
            contentScale = ContentScale.FillHeight,
            colorFilter = ColorFilter.tint(Color.White),
            alpha = 0.15f
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween // Space content between top and bottom
        ) {
            // Top line
            Spacer(modifier = Modifier.height(22.dp)) // Space between top of the screen and the line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.height(31.2.dp)) // Space between top line and text

            // Centered content between the lines
            Column( //column that contains all the text/values
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 11.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.get_ready),
                    fontSize = 50.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp)) // Space between lines of text
                Text(
                    text = stringResource(id = R.string.round),
                    fontSize = 40.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = roundNumber.toString(),
                    fontSize = 30.sp,
                    color = Color.Yellow,
                    textAlign = TextAlign.Center
                )
            }

            // Bottom line

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.height(31.2.dp)) // Space between bottom line and bottom of the screen
        }
    }
}



@Preview(showBackground = true, widthDp = 800, heightDp = 360)
@Composable
fun ReadyScreenPreview(){
ReadyScreen(
roundNumber = 1, rememberNavController())
}