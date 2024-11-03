package com.cs467.helldivers2_stratagemheromobile.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs467.helldivers2_stratagemheromobile.R
import com.cs467.helldivers2_stratagemheromobile.Util.StratagemListUtil
//import com.cs467.helldivers2_stratagemheromobile.data.StratagemDataSource
import com.cs467.helldivers2_stratagemheromobile.model.Stratagem
import com.cs467.helldivers2_stratagemheromobile.model.StratagemInput

//Round
//2
//SCORE with number right above

//
//
//
//
//
//
//
//
//


@Composable
fun StratagemDisplay(stratagem: Stratagem) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = stratagem.imageResourceID),
            contentDescription = null, // Provide a content description for accessibility
            modifier = Modifier.size(128.dp) // Adjust size as needed
        )
        Text(text = stringResource(id = stratagem.stratagemNameResourceID))

        // Display the expected input
        Text(text = "Expected Input: ${stratagem.stratagemInputExpected.joinToString(", ") { it.toString() }}")
    }
}
@Composable
fun GameplayScreen(round: Int, score: Int) {
    //val dataSource = StratagemDataSource()
    //val stratagems = dataSource.loadStratagems()
    val stratagems = StratagemListUtil().getStratagemList(LocalContext.current)

    // Use a Column to stack the round, score, and the list of stratagems
    Column {
        Text(
            text = stringResource(id = R.string.round) + " $round"
        )
        Text(
            text = stringResource(id = R.string.score) + " $score"
        )
//BUTTONS TO INTERACT WITH THE GAME BELOW- Can alter the color of text, and functions as needed; button aesthetic is correct

        //Button
        IconButton(onClick = {
            // TODO: Handle "Left" button click
        }) {
            Image(
                painter = painterResource(id = R.drawable.left_arrow), // replace with your actual drawable resource
                contentDescription = "left arrow"
            )
        }

        IconButton(onClick = {
            // TODO: Handle "Right" button click
        }) {
            Image(
                painter = painterResource(id = R.drawable.right_arrow), // replace with your actual drawable resource
                contentDescription = "right arrow"
            )        }
        IconButton(onClick = {
            // TODO: Handle "Down" button click
        }) {
            Image(
                painter = painterResource(id = R.drawable.down_arrow), // replace with your actual drawable resource
                contentDescription = "down arrow"
            )        }
        IconButton(onClick = {
            // TODO: Handle "Up" button click
        }) {
            Image(
                painter = painterResource(id = R.drawable.up_arrow), // replace with your actual drawable resource
                contentDescription = "up arrow"
            )        }

        // Display the list of stratagems
        LazyColumn {
            items(stratagems) { stratagem ->
                StratagemDisplay(stratagem = stratagem)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameplayScreenPreview() {
    // Sample round and score values
    val sampleRound = 1
    val sampleScore = 100

    // Call the actual GameplayScreen composable with the sample values
    GameplayScreen(round = sampleRound, score = sampleScore)
}