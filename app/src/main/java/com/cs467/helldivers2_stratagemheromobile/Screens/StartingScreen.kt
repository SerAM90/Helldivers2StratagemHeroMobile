package com.cs467.helldivers2_stratagemheromobile.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs467.helldivers2_stratagemheromobile.R

//STRATAGEM HERO
//Enter any Stratagem Input to Start!
@Composable
fun StartingScreen(
    navController: NavController
) {
    Surface(
        onClick = {
            navController.navigate("ready_screen")
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.title_start_screen),
                fontSize = 50.sp
            )
            Text(
                text = stringResource(id = R.string.start_screen_instructions),
                fontSize = 30.sp
            )
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