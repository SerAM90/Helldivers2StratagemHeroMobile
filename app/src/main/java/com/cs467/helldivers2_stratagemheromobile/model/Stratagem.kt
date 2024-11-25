package com.cs467.helldivers2_stratagemheromobile.model

//import android.renderscript.ScriptGroup.Input
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Stratagem(
    val stratagemName: String,
    val imageResourceName: String,
    @StringRes @Transient var stratagemNameResourceID: Int = 0,
    @DrawableRes @Transient var imageResourceID: Int = 0,
    val stratagemInputExpected: List<StratagemInput>
    )

enum class StratagemInput{
    LEFT, RIGHT, UP, DOWN
}