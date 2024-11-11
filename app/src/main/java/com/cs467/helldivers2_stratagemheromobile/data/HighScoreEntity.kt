package com.cs467.helldivers2_stratagemheromobile.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cs467.helldivers2_stratagemheromobile.Util.Constants.HS_TABLE

@Entity(tableName = HS_TABLE)
data class HighScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val scoreId: Int = 0,
    @ColumnInfo(name = "player_name")
    val playerName: String,
    @ColumnInfo(name = "highscore_val")
    val playerScore: Int
)
