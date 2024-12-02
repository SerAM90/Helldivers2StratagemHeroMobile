package com.cs467.helldivers2_stratagemheromobile.Screens

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

    // Trigger navigation to the GameplayScreen after a delay
    LaunchedEffect(true) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.sound_end_round_triple_score)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.reset()
            it.release() // Release resources after sound completes
        }
        delay(4000) // Delay to allow sound and transitions
        mainViewModel.goToNextRound()
        navController.navigate("ready_screen") {
            popUpTo("after_round_screen") { inclusive = true }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(R.drawable.superearthbackground),
            contentDescription = "Super Earth Logo",
            modifier = Modifier
                .padding(top = 4.dp) // Add padding to move the image away from the top
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            contentScale = ContentScale.FillHeight,
            colorFilter = ColorFilter.tint(Color.White),
            alpha = 0.15f
        )

        // Foreground content
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween // Space elements between top and bottom
        ) {
            // Top line
            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(Color.White)
            )

            // Centered content between the lines
            Column(
                modifier = Modifier
                    .weight(1f) // Dynamic space allocation
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Score information
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.round_bonus),
                        fontSize = 30.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$roundBonus",
                        fontSize = 30.sp,
                        color = Color.Yellow
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.time_bonus),
                        fontSize = 30.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$timeBonus",
                        fontSize = 30.sp,
                        color = Color.Yellow
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.perfect_bonus),
                        fontSize = 30.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$perfectBonus",
                        fontSize = 30.sp,
                        color = Color.Yellow
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.total_score),
                        fontSize = 30.sp,
                        color = Color.White
                    )
                    Text(
                        text = "$totalScore",
                        fontSize = 30.sp,
                        color = Color.Yellow
                    )
                }
            }

            // Bottom line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.height(48.dp))

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