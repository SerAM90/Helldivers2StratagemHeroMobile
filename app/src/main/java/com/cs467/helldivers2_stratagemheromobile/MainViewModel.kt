package com.cs467.helldivers2_stratagemheromobile

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.cs467.helldivers2_stratagemheromobile.model.Stratagem
import com.cs467.helldivers2_stratagemheromobile.Util.StratagemListUtil
import com.cs467.helldivers2_stratagemheromobile.model.StratagemInput

class MainViewModel: ViewModel() {
    var isPlaying by mutableStateOf(false)

    var score by mutableIntStateOf(0)
        private set

    var round by mutableIntStateOf(1)
        private set

    // This holds the list of stratagems for the round
    private val _stratagems = mutableStateListOf<Stratagem>()
    val stratagems: List<Stratagem> = _stratagems

    var correctCount by mutableIntStateOf(0)
        private set

    @Composable
    fun pickStratagems() {
        val numStratagems = minOf(round + 3, 16)
        // Get the list of stratagems, shuffle them randomly, then only return as many needed for
        // the round (ex: round 3 requires 6 stratagems)
        _stratagems.clear()
        _stratagems.addAll(StratagemListUtil().getStratagemList(LocalContext.current)
            .shuffled()
            .subList(0, numStratagems))
    }

    fun checkSwipe(direction: StratagemInput) {
        // We check from back to front because it is more efficient to pop the last element in the
        // stratagems array than to remove the first and reorder the queue
        val currentStratagem = stratagems.last()

        // Correct swipe
        if (direction == currentStratagem.stratagemInputExpected[correctCount]) {
            correctCount++

            // We have completed all swipes for the current stratagem and need to move on to the
            // next one
            if (correctCount == currentStratagem.stratagemInputExpected.size) {
                // Update score
                score += currentStratagem.stratagemInputExpected.size * 5
                _stratagems.removeLast()
                correctCount = 0
            }
        } else { // Incorrect swipe
            correctCount = 0
        }
    }
}