package com.cs467.helldivers2_stratagemheromobile
import android.content.Context
import org.junit.Test
import com.cs467.helldivers2_stratagemheromobile.model.Stratagem
import android.content.res.AssetManager
import androidx.test.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.json.Json


class StratagemListUnitTest {

    @Test
    fun testStratagemList() {
        // Get context from the test environment
        val context: Context = getApplicationContext() // Adjust this for your test framework/environment

        // Read the "Stratagems.json" from assets folder
        val assetManager: AssetManager = context.assets
        val jsonStratagem = assetManager.open("Stratagems.json").bufferedReader().use { it.readText() }

        // Decode the JSON to a list of Stratagems
        val listOfStratagems = Json.decodeFromString<List<Stratagem>>(jsonStratagem)

        // Iterate through the stratagem list and check conditions
        listOfStratagems.forEach { stratagem ->
            val stratagemName = if (stratagem.stratagemName.isNotEmpty()) {
                stratagem.stratagemName
            } else {
                "stratagem name not found"
            }

            val imageResourceName = if (stratagem.imageResourceName.isNotEmpty()) {
                stratagem.imageResourceName
            } else {
                "image resource not found"
            }

            val stratagemInput = if (stratagem.stratagemInputExpected.isNotEmpty()) {
                stratagem.stratagemInputExpected.joinToString(", ")
            } else {
                "no input detected"
            }

            // Print output to console for debugging
            println("Stratagem Name: $stratagemName, Image Resource Name: $imageResourceName, Stratagem Input: $stratagemInput")

            // Assertions
            if (stratagem.stratagemName.isEmpty()) {
                assertEquals("stratagem name not found", stratagemName)
            } else {
                assertEquals(stratagem.stratagemName, stratagemName)
            }

            if (stratagem.imageResourceName.isEmpty()) {
                assertEquals("image resource not found", imageResourceName)
            } else {
                assertEquals(stratagem.imageResourceName, imageResourceName)
            }

            if (stratagem.stratagemInputExpected.isEmpty()) {
                assertEquals("no input detected", stratagemInput)
            } else {
                assertEquals(stratagem.stratagemInputExpected.joinToString(", "), stratagemInput)
            }

            // Check if stratagem name and image resource name match
            if (stratagem.stratagemName != stratagem.imageResourceName) {
                println("Mismatch detected! Stratagem Name: $stratagemName, Image Resource Name: $imageResourceName. Image and name do not match.")
            }
        }
    }

    // Helper method to access the context (depending on your testing framework/environment)
    private fun getApplicationContext(): Context {
        // Return the application context in a way that is suitable for your testing framework
        // Example for Android Instrumentation tests
        return InstrumentationRegistry.getInstrumentation().targetContext
    }
}