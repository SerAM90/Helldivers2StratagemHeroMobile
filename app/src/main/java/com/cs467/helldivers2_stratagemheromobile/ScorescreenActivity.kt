package com.cs467.helldivers2_stratagemheromobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.cs467.helldivers2_stratagemheromobile.Util.Constants.HS_DATABASE
import com.cs467.helldivers2_stratagemheromobile.adapter.ScoreAdapter
import com.cs467.helldivers2_stratagemheromobile.data.HighScoreDatabase
import com.cs467.helldivers2_stratagemheromobile.databinding.ActivityScorescreenBinding

class ScorescreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityScorescreenBinding
    private val highscoreDB : HighScoreDatabase by lazy {
        Room.databaseBuilder(this,HighScoreDatabase::class.java,HS_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val scoreAdapter by lazy { ScoreAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityScorescreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddScore.setOnClickListener {
            startActivity(Intent(this,AddEntryActivity::class.java))
        }

        enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem(){
        binding.apply {
            if(highscoreDB.dao().getAllScores().isNotEmpty()){
                rvScoreList.visibility= View.VISIBLE
                tvEmptyText.visibility=View.GONE
                scoreAdapter.differ.submitList(highscoreDB.dao().getAllScores())
            }
            else{
                rvScoreList.visibility=View.GONE
                tvEmptyText.visibility=View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView(){
        binding.rvScoreList.apply {
            layoutManager=LinearLayoutManager(this@ScorescreenActivity)
            adapter=scoreAdapter
        }
    }
}