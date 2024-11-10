package com.cs467.helldivers2_stratagemheromobile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.cs467.helldivers2_stratagemheromobile.model.Stratagem
import com.cs467.helldivers2_stratagemheromobile.Util.StratagemListUtil

class MainViewModel: ViewModel() {
    var isPlaying by mutableStateOf(false)

    var score by mutableIntStateOf(0)
        private set

    var round by mutableIntStateOf(1)
        private set

    @Composable
    fun pickStratagems(): List<Stratagem> {
        val numStratagems = round + 3
        // Get the list of stratagems, shuffle them randomly, then only return as many needed for
        // the round (ex: round 3 requires 6 stratagems)
        return StratagemListUtil().getStratagemList(LocalContext.current)
            .shuffled()
            .subList(0, numStratagems)
    }
}