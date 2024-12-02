package com.cs467.helldivers2_stratagemheromobile.data

import androidx.room.*
import com.cs467.helldivers2_stratagemheromobile.Util.Constants.HS_TABLE

@Dao
interface HighScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScore(hsEntity: HighScoreEntity)

    @Update
    fun updateScore(hsEntity: HighScoreEntity)

    @Delete
    fun deleteScore(hsEntity: HighScoreEntity)

    @Query("SELECT * FROM $HS_TABLE ORDER BY player_score DESC LIMIT 3")
    fun getTopScores(): List<HighScoreEntity>
    //above should be suspend fun? may need to test further

    @Query("SELECT * FROM $HS_TABLE ORDER BY player_score DESC")
    fun getAllScores() : MutableList<HighScoreEntity>

    @Query("SELECT * FROM $HS_TABLE WHERE scoreId LIKE :id")
    fun getScore(id : Int) : HighScoreEntity

    @Query("SELECT player_score FROM $HS_TABLE ORDER BY player_score DESC LIMIT 1 OFFSET 2")
    fun getLowestHS(): Int

    //@Query("INSERT INTO $HS_TABLE (player_name, player_score) VALUES ('Jonny', 5)")
    //fun insertExample() : HighScoreEntity
}