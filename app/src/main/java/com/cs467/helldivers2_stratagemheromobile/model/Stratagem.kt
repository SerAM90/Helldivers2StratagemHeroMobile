package com.cs467.helldivers2_stratagemheromobile.model

import android.renderscript.ScriptGroup.Input
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Stratagem(
    @StringRes val stratagemName: Int,
    @DrawableRes val imageResourceID: Int,
    val stratagemInputExpected: List<StratagemInput>, //maybe the inputs need to be held in a list?
)

enum class StratagemInput{
    LEFT, RIGHT, UP, DOWN
}