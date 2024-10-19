package com.cs467.helldivers2_stratagemheromobile.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.cs467.helldivers2_stratagemheromobile.R

//GET READY
//Round
//1 (round number)
//Some kind of phrase at the bottom of the screen?****************************************
@Composable
fun ReadyScreen(readyDisplay: String, round: String, roundNumber: Int, modifier: Modifier)
{
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = readyDisplay,
                fontSize = 50.sp
            )

            Text(
                text = round,
                fontSize = 40.sp
            )
            Text(
                text = roundNumber.toString(),
                fontSize = 30.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReadyScreenPreview(){
ReadyScreen(
readyDisplay = stringResource(id = R.string.get_ready),
round = stringResource(id = R.string.round),
roundNumber = 1,
modifier = Modifier
)
}