package com.cs467.helldivers2_stratagemheromobile.Screens

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs467.helldivers2_stratagemheromobile.MainViewModel
import com.cs467.helldivers2_stratagemheromobile.R
import kotlinx.coroutines.delay

//Round Bonus 75
//Time Bonus 64
//Perfect Bonus 89
//Total Score 23249


@Composable
        /**
         * Function below is the AfterRoundScreen. It is displayed after a round is completed (complete round 1->AfterRoundScreen->Round 2
         * The screen tracks the bonus, time bonus, perfect bonus, and the total score.
         */
fun AfterRoundScreen(
    roundBonus: Int,
    timeBonus: Int,
    perfectBonus: Int,
    totalScore: Int,
    modifier: Modifier,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    // Trigger navigation to the GameplayScreen after 2 seconds
    LaunchedEffect(true) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.sound_end_round_triple_score) // Add a sound file in 'res/raw'
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.reset()
            it.release() // Release the MediaPlayer when the sound is done
        }
        delay(4000) //default is 2000, 5000 for testing- SLOW TIME BETWEEN TRANSITION FOR MATH CHECK******************
        mainViewModel.goToNextRound() //goes to the next round for continued play- increases the round, and stratagems displayed per pickStratagems() function
        navController.navigate("ready_screen") {
            popUpTo("after_round_screen") { inclusive = true }
        }
    }

    Box( //bg box
        modifier = modifier.fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(R.drawable.superearthbackground),
            contentDescription = "Super Earth Logo",
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            contentScale = ContentScale.FillHeight,
            colorFilter = ColorFilter.tint(Color.White),
            alpha = 0.15f
        )

        // Box for score names and values
        Box(
            modifier = Modifier
                .padding(32.dp) // Adds spacing between the box and the screen edges
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(16.dp))
                .padding(16.dp) // Padding inside the box
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Row for round bonus
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.round_bonus),
                        fontSize = 40.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$roundBonus",
                        fontSize = 40.sp,
                        color = Color.Yellow
                    )
                }

                // Row for time bonus
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.time_bonus),
                        fontSize = 40.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$timeBonus",
                        fontSize = 40.sp,
                        color = Color.Yellow
                    )
                }

                // Row for perfect bonus
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.perfect_bonus),
                        fontSize = 40.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$perfectBonus",
                        fontSize = 40.sp,
                        color = Color.Yellow
                    )
                }

                // Row for total score
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.total_score),
                        fontSize = 40.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$totalScore",
                        fontSize = 40.sp,
                        color = Color.Yellow
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 800, heightDp = 360)
@Composable
fun AfterRoundScreenPreview(){
    AfterRoundScreen(roundBonus = 100,
        timeBonus = 200,
        perfectBonus = 50,
        totalScore = 350,
        modifier = Modifier,
        navController = rememberNavController(),
        mainViewModel = MainViewModel())
}