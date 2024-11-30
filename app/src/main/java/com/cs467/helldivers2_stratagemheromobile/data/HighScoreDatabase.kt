package com.cs467.helldivers2_stratagemheromobile.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cs467.helldivers2_stratagemheromobile.Util.Constants.HS_DATABASE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//For testing purposes, need to update version every time new database instance created
@Database(entities = [HighScoreEntity::class], version = 4, exportSchema = false)
abstract class HighScoreDatabase : RoomDatabase(){
    abstract fun hsDao():HighScoreDao

    companion object {
        @Volatile
        private var INSTANCE: HighScoreDatabase? = null

        fun getDatabase(context: Context): HighScoreDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HighScoreDatabase::class.java,
                    HS_DATABASE)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                // Get the DAO
                                val dao = getDatabase(context).hsDao()
                                // Insert sample data
                                dao.insertScore(HighScoreEntity(playerName = "Sam", playerScore = 500))
                                dao.insertScore(HighScoreEntity(playerName = "Bob", playerScore = 42))
                                dao.insertScore(HighScoreEntity(playerName = "Monk", playerScore = 8989))
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class HighScoreDatabaseCallback(private val dao: HighScoreDao) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Insert sample data in a coroutine
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertScore(HighScoreEntity(playerName = "Sam", playerScore = 500))
            dao.insertScore(HighScoreEntity(playerName = "Bob", playerScore = 42))
            dao.insertScore(HighScoreEntity(playerName = "Monk", playerScore = 8989))
        }
    }
}