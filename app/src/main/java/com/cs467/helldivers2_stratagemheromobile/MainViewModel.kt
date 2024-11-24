package com.cs467.helldivers2_stratagemheromobile

import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(): ViewModel() {
    var isPlaying by mutableStateOf(false)

    var score by mutableIntStateOf(0)

    var round by mutableIntStateOf(1)
        private set

    var wrongInput by mutableStateOf(false)

    var perfectRound by mutableStateOf(true) //track a perfect found->perfect round flag
        private set

    // This holds the list of stratagems for the round
    private val _stratagems = mutableStateListOf<Stratagem>()
    val stratagems: List<Stratagem> = _stratagems

    var correctCount by mutableIntStateOf(0)
        private set
    
    private val _roundFinished = MutableStateFlow<Boolean>(false)
    val roundFinished: StateFlow<Boolean> = _roundFinished.asStateFlow()
    

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
                if (_stratagems.size == 1) {
                    _roundFinished.value = true
                }//checking to see if _stratagems is down to the last element
                else{
                    _stratagems.removeLast()
                }
                correctCount = 0

            }

        } else { // Incorrect swipe
            correctCount = 0
            wrongInput = true
            perfectRound = false //messed up the input->not a perfect round flag
            Log.d("PerfectRound", "Swipe incorrect, setting perfectRound to false")
        }
    }

    fun resetForNewRound(){
        _roundFinished.value = false
        perfectRound = true
        Log.d("PerfectRound", "Resetting for new round, setting perfectRound to true")
        Log.d("PerfectRound", "Reset for new round. Perfect round = $perfectRound")


    }

    fun goToNextRound(){ //increase the round by 1
        round ++
    }

    fun roundBonusScore(): Int {
        return 75 + (round - 1) * 25
    }
    fun roundPerfectBonusScore(): Int{
        return if (perfectRound) 100 else 0
    }
}