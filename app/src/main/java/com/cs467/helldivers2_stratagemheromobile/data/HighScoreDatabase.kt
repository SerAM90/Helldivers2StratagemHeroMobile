package com.cs467.helldivers2_stratagemheromobile.data

import androidx.room.*

@Database(entities = [HighScoreEntity::class], version = 1, exportSchema = false)
abstract class HighScoreDatabase : RoomDatabase(){
    abstract fun dao():HighScoreDao
}