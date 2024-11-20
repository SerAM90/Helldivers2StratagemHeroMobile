package com.cs467.helldivers2_stratagemheromobile.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
    // Trigger navigation to the GameplayScreen after 2 seconds
    LaunchedEffect(true) {
        delay(2000)
        navController.navigate("gameplay_screen") {
            popUpTo("after_round_screen") { inclusive = true }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.round_bonus) + " $roundBonus",
                fontSize = 40.sp
            )
            Text(
                text = stringResource(id = R.string.time_bonus) + " $timeBonus",
                fontSize = 40.sp
            )
            Text(
                text = stringResource(id = R.string.perfect_bonus) + " $perfectBonus",
                fontSize = 40.sp
            )
            Text(
                text = stringResource(id = R.string.total_score) + " $totalScore",
                fontSize = 40.sp
            )
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun AfterRoundScreenPreview(){
//    AfterRoundScreen(roundBonus = 100, timeBonus = 200, perfectBonus = 50, totalScore = 350, modifier = Modifier, navController = rememberNavController(), mainViewModel: MainViewModel)
//}