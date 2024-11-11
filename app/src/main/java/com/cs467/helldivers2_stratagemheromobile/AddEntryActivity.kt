package com.cs467.helldivers2_stratagemheromobile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Snackbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.cs467.helldivers2_stratagemheromobile.Util.Constants.HS_DATABASE
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreDatabase
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreEntity
import com.cs467.helldivers2_stratagemheromobile.databinding.ActivityAddEntryBinding

class AddEntryActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddEntryBinding
    private val highscoreDB : HighScoreDatabase by lazy {
        Room.databaseBuilder(this,HighScoreDatabase::class.java,HS_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var highScoreEntity: HighScoreEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSave.setOnClickListener {
                val pname = edtTitle.text.toString()
                val pscore = edtDesc.text.toString().toInt()

                if (pname.isNotEmpty()){
                    highScoreEntity= HighScoreEntity(0,pname,pscore)
                    highscoreDB.dao().insertScore(highScoreEntity)
                    finish()

                }
                else{
                    com.google.android.material.snackbar.Snackbar.make(it,"Please enter all fields",com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show()
                }
            }
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}