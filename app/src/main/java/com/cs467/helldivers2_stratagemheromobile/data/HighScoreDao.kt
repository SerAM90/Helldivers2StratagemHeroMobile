package com.cs467.helldivers2_stratagemheromobile.data

import androidx.room.*
import com.cs467.helldivers2_stratagemheromobile.Util.Constants.HS_TABLE

//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Update

@Dao
interface HighScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScore(hsEntity: HighScoreEntity)

    @Update
    fun updateScore(hsEntity: HighScoreEntity)

    @Delete
    fun deleteScore(hsEntity: HighScoreEntity)

    @Query("SELECT * FROM $HS_TABLE ORDER BY highscore_val DESC")
    fun getAllScores() : MutableList<HighScoreEntity>

    @Query("SELECT * FROM $HS_TABLE WHERE scoreId LIKE :id")
    fun getScore(id : Int) : HighScoreEntity

}