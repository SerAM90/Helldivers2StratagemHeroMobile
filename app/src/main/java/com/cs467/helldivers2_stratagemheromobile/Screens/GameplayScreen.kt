package com.cs467.helldivers2_stratagemheromobile.Screens

import android.util.Log
import androidx.activity.viewModels
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs467.helldivers2_stratagemheromobile.MainViewModel
import com.cs467.helldivers2_stratagemheromobile.R
import com.cs467.helldivers2_stratagemheromobile.Util.StratagemListUtil
import com.cs467.helldivers2_stratagemheromobile.model.Stratagem
import kotlinx.coroutines.delay

/**
 * The function below is for our GameplayScreen. It has the round, and int. It as well displays our
 * stratagems using the StratagemDisplay function. The StratagemDisplay() shows our stratagems in the round.
 */
@Composable
fun GameplayScreen(mainViewModel: MainViewModel, navController: NavController) {
    val stratagems = mainViewModel.stratagems
    val correctCount = mainViewModel.correctCount
    val roundFinished by mainViewModel.roundFinished.collectAsState()

    LaunchedEffect(roundFinished){
        if(roundFinished){
            navController.navigate("after_round_screen")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(33, 33, 33)),
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
                    Text(text = "${mainViewModel.round}", color = Color.Yellow, fontSize = 30.sp)
                }
            }

            StratagemDisplay(stratagems = stratagems, correctCount = correctCount, navController)

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = stringResource(id = R.string.score) + " ${mainViewModel.score}",
                    color = Color.White
                )
            }
        }
    }
}

/**
 * The StratagemDisplay function below displays our Stratagems from the StratagemListUtil file. Each Stratagem has an associated name, image, and expected input that is needed for the stratagem.
 */
 @Composable
fun StratagemDisplay(stratagems: List<Stratagem>, correctCount: Int, navController: NavController) {
    // Logging the current stratagems
//    stratagems.forEachIndexed { index, stratagem ->
//        Log.d("StratagemDisplay", "Stratagem #$index:")
//        Log.d("StratagemDisplay", "  Name: ${stratagem.stratagemName}")
//        Log.d("StratagemDisplay", "  Image Resource: ${stratagem.imageResourceName}")
//        Log.d("StratagemDisplay", "  Expected Input: ${stratagem.stratagemInputExpected}")
//    }
    val inputToResourceMap = StratagemListUtil().getInputToResourceMap(LocalContext.current)
    val stratagem = stratagems.last()

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
                modifier = Modifier
                    .size(90.dp)
                    .border(3.dp, Color.Yellow) // Adjust size as needed
            )
            // Display the stratagems in queue
            for (i in minOf(stratagems.size - 2, 4) downTo 0) {
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
            modifier = Modifier
                .fillMaxWidth()
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
            stratagem.stratagemInputExpected.forEachIndexed { index, stratagemInput ->
                Image(
                    painter = painterResource(id = inputToResourceMap[stratagemInput]!!),
                    contentDescription = null, // Provide a content description for accessibility
                    modifier = Modifier.size(40.dp), // Adjust size as needed
                    colorFilter = if (index + 1 <= correctCount) ColorFilter.tint(color = Color.Yellow) else null
                )
            }
        }

        // Timer
        Timer(
            totalTime = 10L * 1000L, //10 seconds is 10L * 1000L **Test on 100L * 1000L
            modifier = Modifier,
            stratagemCount = stratagems.size,
            navController = navController
        )
    }
}

@Composable
fun Timer (
    totalTime: Long,
    modifier: Modifier,
    stratagemCount: Int,
    inactiveBarColor: Color = Color.LightGray,
    activeBarColor: Color = Color.Yellow,
    initialValue: Float = 1.0f,
    strokeWidth: Dp = 15.dp,
    navController: NavController
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

    // This function runs only once in the beginning and starts the timer
    LaunchedEffect(Unit) {
        delay(100L)
        isTimerRunning = true
    }

    // This function handles decreasing the timer
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(50L)
            currentTime -= 50L
            value = currentTime / totalTime.toFloat()
        } else if (currentTime <= 0) {
            isTimerRunning = false
            navController.navigate("game_over_screen")
        }
    }

    // This function is run everytime the number of stratagems in a round decreases
    // In other words, everytime a stratagem is completed
    LaunchedEffect(key1 = stratagemCount) {
        val secondsToAdd = 2L  // Adjust this as required, adds 2 seconds for now
        currentTime = minOf(currentTime + (secondsToAdd * 1000L), totalTime)
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
    val viewModel = MainViewModel()
    viewModel.pickStratagems()
    // Call the actual GameplayScreen composable with the sample values
    GameplayScreen(viewModel, rememberNavController())
}
