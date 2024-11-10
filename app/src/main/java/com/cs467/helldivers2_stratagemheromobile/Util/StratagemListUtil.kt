package com.cs467.helldivers2_stratagemheromobile.Util

import android.annotation.SuppressLint
import android.content.Context
import com.cs467.helldivers2_stratagemheromobile.model.Stratagem
import com.cs467.helldivers2_stratagemheromobile.model.StratagemInput
import kotlinx.serialization.json.Json

class StratagemListUtil {
    @SuppressLint("DiscouragedApi")
    fun getStratagemList(context: Context): List<Stratagem>{
        val assetManager = context.assets
        val jsonStratagem = assetManager.open("Stratagems.json").bufferedReader().use { it.readText() }//File("app/src/main/assets/Stratagems.json").readText() //read from JSON list
        val listOfStratagems = Json.decodeFromString<List<Stratagem>>(jsonStratagem) //decode JSON text from first step, to stratagem list
        listOfStratagems.forEach{stratagem ->
            stratagem.stratagemNameResourceID = context.resources.getIdentifier(stratagem.stratagemName, "string", context.packageName)
            stratagem.imageResourceID = context.resources.getIdentifier(stratagem.imageResourceName, "drawable", context.packageName)
        }
        return listOfStratagems //return the list of stratagems
    }

    @SuppressLint("DiscouragedApi")
    fun getInputToResourceMap(context: Context): Map<StratagemInput, Int>{
        return mapOf(
            StratagemInput.RIGHT to context.resources.getIdentifier("right_arrow", "drawable", context.packageName),
            StratagemInput.LEFT to context.resources.getIdentifier("left_arrow", "drawable", context.packageName),
            StratagemInput.DOWN to context.resources.getIdentifier("down_arrow", "drawable", context.packageName),
            StratagemInput.UP to context.resources.getIdentifier("up_arrow", "drawable", context.packageName),
        )
    }

}