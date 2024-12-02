package com.cs467.helldivers2_stratagemheromobile.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs467.helldivers2_stratagemheromobile.MainViewModel
import com.cs467.helldivers2_stratagemheromobile.R
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreDao
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreDatabase
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreEntity
import kotlinx.coroutines.delay
import kotlin.random.Random

//GAME OVER
//HIGH SCORES
//1-AAAA I 009230 //highscores probably need to be a class or data object
//2- BBB I 343980023
//4- CCC I 304930423  | <--- vertical bar lol, goes on scores between name and score
//YOUR FINAL SCORE
//123

data class Score(val playerName: String, val score: Int)

@Composable
        /**
         * function below is the display for our GameOverScreen. This screen is displayed when the user fails a round (we do not have a quit round but this is intended)
         * This screen will display the Final Score the player achieved this session.
         * It will also display the Top Three scores recorded within the game's history, and the name of the player will be displayed along with the top 3 scores.
         */
fun GameOverScreen(
    //This should be a string resource
    gameOverDisplay: String,
    threeTopScores: List<HighScoreEntity>,
    finalScore: HighScoreEntity,
    modifier : Modifier,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    //val context = LocalContext.current
    // onClick- so on the GameOverScreen, if you click it will take you back to the StartingScreen
    Surface(
        modifier = modifier.fillMaxSize(),
        onClick = {
            //Go to the "ready_screen" when clicked
            navController.navigate("starting_screen") {
            }
        }
    ) {
        Box(
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.game_over),
                color = Color.White,
                fontSize = 50.sp)
            Text(text = stringResource(id = R.string.high_scores),
                color = Color.White,
                fontSize = 30.sp)
            for ((index, score) in threeTopScores.withIndex()) {
                Row {
                    Text(
                        text = "${index + 1}. ${score.playerName} | ${score.playerScore}",
                        color = Color.White,
                        fontSize = 25.sp
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.your_final_score),
                color = Color.White,
                fontSize = 25.sp
            )
            Text(text= "${finalScore.playerScore}",
                color = Color.White,
                fontSize = 35.sp)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 360)
@Composable
fun GameOverScreenPreview() {
    val topScores = listOf(
        HighScoreEntity(1, "Tater Salad", 1500),
        HighScoreEntity(2, "Pasta Salad", 1200),
        HighScoreEntity(3, "Chicken Salad", 1000)
    )

    val finalScore = HighScoreEntity(4, "test", 200)

    GameOverScreen(
        gameOverDisplay = stringResource(id = R.string.game_over),
        threeTopScores = topScores,
        finalScore = finalScore,
        modifier = Modifier,
        mainViewModel = MainViewModel(),
        navController = rememberNavController()
    )
}