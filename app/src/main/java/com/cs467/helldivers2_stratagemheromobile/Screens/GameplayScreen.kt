package com.cs467.helldivers2_stratagemheromobile.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cs467.helldivers2_stratagemheromobile.R
import com.cs467.helldivers2_stratagemheromobile.Util.StratagemListUtil
import com.cs467.helldivers2_stratagemheromobile.model.Stratagem

@Composable
        /**
         * The function below is for our GameplayScreen. It has the round, and int. It as well displays our
         * stratagems using the StratagemDisplay function. The StratagemDisplay() shows our stratagems in the round.
         */
fun GameplayScreen(round: Int, score: Int, stratagems: List<Stratagem>) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(33,33,33)),
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.20f)
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.End
            ) {
                Column {
                    Text(text = stringResource(id = R.string.round), color = Color.White)
                    Text(text = "$round", color = Color.Yellow, fontSize = 30.sp)
                }
            }

            StratagemDisplay(stratagems = stratagems)

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = stringResource(id = R.string.score) + " $score",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
        /**
         * The StratagemDisplay function below displays our Stratagems from the StratagemListUtil file. Each Stratagem has an associated name, image, and expected input that is needed for the stratagem.
         */
fun StratagemDisplay(stratagems: List<Stratagem>) {
    val inputToResourceMap = StratagemListUtil().getInputToResourceMap(LocalContext.current)
    val stratagem = stratagems.first()

    Column(
        modifier = Modifier.fillMaxWidth(0.75f),
        horizontalAlignment = Alignment.Start
    ) {
        // Stratagem display
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display the main stratagem
            Image(
                painter = painterResource(id = stratagem.imageResourceID),
                contentDescription = null, // Provide a content description for accessibility
                modifier = Modifier.size(90.dp).border(3.dp, Color.Yellow) // Adjust size as needed
            )
            // Display the stratagems in queue
            for (i in 1..5) {
                if (i < stratagems.size) {
                    Image(
                        painter = painterResource(id = stratagems[i].imageResourceID),
                        contentDescription = null, // Provide a content description for accessibility
                        modifier = Modifier
                            .size(75.dp)
                            .alpha(0.8f) // Adjust size as needed
                    )
                }
            }
        }

        // Stratagem name
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Yellow)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = stratagem.stratagemNameResourceID)
            )
        }


        // Expected input (arrows)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (input in stratagem.stratagemInputExpected) {
                Image(
                    painter = painterResource(id = inputToResourceMap[input]!!),
                    contentDescription = null, // Provide a content description for accessibility
                    modifier = Modifier.size(40.dp) // Adjust size as needed
                )
            }
        }

        // Timer
        Timer(
            totalTime = 4,
            handleColor = Color.DarkGray,
            modifier = Modifier
        )
    }
}

@Composable
fun Timer (
    totalTime: Long,
    handleColor: Color,
    modifier: Modifier,
    inactiveBarColor: Color = Color.LightGray,
    activeBarColor: Color = Color.Yellow,
    initialValue: Float = 0.5f,
    strokeWidth: Dp = 15.dp
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .onSizeChanged {
                size = it
            }
            .fillMaxWidth()
            .height(strokeWidth)
            .padding(0.dp, 20.dp)
    ) {
        Canvas(modifier = modifier) {
            drawLine(
                color = inactiveBarColor,
                start = Offset(0f, strokeWidth.toPx() / 2),
                end = Offset(size.width.toFloat(), strokeWidth.toPx() / 2),
                strokeWidth = strokeWidth.toPx()
            )
            drawLine(
                color = activeBarColor,
                start = Offset(0f, strokeWidth.toPx() / 2),
                end = Offset(size.width.toFloat() * value, strokeWidth.toPx() / 2),
                strokeWidth = strokeWidth.toPx()
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 360)
@Composable
fun GameplayScreenPreview() {
    // Sample round and score values
    val sampleRound = 1
    val sampleScore = 100
    val sampleStratagems = StratagemListUtil().getStratagemList(LocalContext.current).shuffled().subList(0, 4)
    // Call the actual GameplayScreen composable with the sample values
    GameplayScreen(round = sampleRound, score = sampleScore, stratagems = sampleStratagems)
}


//BUTTONS TO INTERACT WITH THE GAME BELOW- Can alter the color of text, and functions as needed; button aesthetic is correct
// Commented out while we do the swipe mechanics. We can bring it back if time permits
//Button
//        IconButton(onClick = {
//            // TODO: Handle "Left" button click
//        }) {
//            Image(
//                painter = painterResource(id = R.drawable.left_arrow), // replace with your actual drawable resource
//                contentDescription = "left arrow"
//            )
//        }
//
//        IconButton(onClick = {
//            // TODO: Handle "Right" button click
//        }) {
//            Image(
//                painter = painterResource(id = R.drawable.right_arrow), // replace with your actual drawable resource
//                contentDescription = "right arrow"
//            )        }
//        IconButton(onClick = {
//            // TODO: Handle "Down" button click
//        }) {
//            Image(
//                painter = painterResource(id = R.drawable.down_arrow), // replace with your actual drawable resource
//                contentDescription = "down arrow"
//            )        }
//        IconButton(onClick = {
//            // TODO: Handle "Up" button click
//        }) {
//            Image(
//                painter = painterResource(id = R.drawable.up_arrow), // replace with your actual drawable resource
//                contentDescription = "up arrow"
//            )        }

// Display the list of stratagems
//            items(stratagems) { stratagem ->


//            }