package com.cs467.helldivers2_stratagemheromobile.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs467.helldivers2_stratagemheromobile.R
import com.cs467.helldivers2_stratagemheromobile.data.StratagemDataSource
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
        Text(text = stringResource(id = stratagem.stratagemName))

        // Display the expected input
        Text(text = "Expected Input: ${stratagem.stratagemInputExpected.joinToString(", ") { it.toString() }}")
    }
}
@Composable
fun GameplayScreen(round: Int, score: Int) {
    val dataSource = StratagemDataSource()
    val stratagems = dataSource.loadStratagems()

    // Use a Column to stack the round, score, and the list of stratagems
    Column {
        Text(
            text = stringResource(id = R.string.round) + " $round"
        )
        Text(
            text = stringResource(id = R.string.score) + " $score"
        )

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

    val sampleStratagem = Stratagem(
        stratagemName = R.string.hellbomb,
        imageResourceID = R.drawable.nux_223_hellbomb,
        stratagemInputExpected = listOf(StratagemInput.DOWN, StratagemInput.UP, StratagemInput.LEFT)
    )


    Box {
        Column {
            Row {
                Text(
                    text = stringResource(id = R.string.round) + " 1" // Example round
                )
                Text(
                    text = stringResource(id = R.string.score) + " 100" // Example score
                )
            }

            // Display the Stratagem
            StratagemDisplay(stratagem = sampleStratagem)
        }
    }
}