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

//STRATAGEM HERO
//Enter any Stratagem Input to Start!


@Composable
fun StartingScreen(
    title: String, instructions: String, modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 50.sp
            )
            Text(
                text = instructions,
                fontSize = 30.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartingScreenPreview(){
    StartingScreen(
        title = stringResource(id = R.string.title_start_screen), instructions = stringResource(
        id = R.string.start_screen_instructions
    ), modifier = Modifier)
}