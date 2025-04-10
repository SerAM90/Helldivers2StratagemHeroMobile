package com.cs467.helldivers2_stratagemheromobile.Screens

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs467.helldivers2_stratagemheromobile.R

//STRATAGEM HERO
//Enter any Stratagem Input to Start!
@Composable
        /**
         * This function is for the StartingScreen. This is the screen displayed when the game is first launched. It displays the title, and instructions.
         */
fun StartingScreen(
    navController: NavController
) {
    val context = LocalContext.current

    // Top-level container - all contained within the box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable {
                navController.navigate("ready_screen")

                // Play start sound
                val mediaPlayer = MediaPlayer.create(context, R.raw.start)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    it.reset()
                    it.release() // Release resources when done
                }
            }
    ) {
        // Background image
        Image(
            painter = painterResource(R.drawable.superearthbackground),
            contentDescription = "Super Earth Logo",
            modifier = Modifier
                .padding(top = 75.dp)
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            contentScale = ContentScale.FillHeight,
            colorFilter = ColorFilter.tint(Color.White),
            alpha = 0.15f
        )

        // Foreground content
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween //Space between lines and edges
        ) {
            // Spacer to create space between the top edge and the top line
            Spacer(modifier = Modifier.height(48.dp))

            // Top white line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.White)
            )

            // Center the content between the lines
            Column( //column for all the text and values on the screen
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // weight for dynamic spacing
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.title_start_screen),
                    fontSize = 50.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp)) // Spacer for the title and instructions
                Text(
                    text = stringResource(R.string.start_screen_instructions),
                    fontSize = 30.sp,
                    color = Color.Yellow
                )
            }

            // Bottom white line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.White)
            )

            // Spacer to create space between the bottom line and the bottom edge of the screen
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 360)
@Composable
fun StartingScreenPreview(){
    StartingScreen(
        navController = rememberNavController()
    )
}