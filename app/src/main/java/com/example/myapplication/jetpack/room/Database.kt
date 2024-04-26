package com.example.myapplication.jetpack.room

import android.content.Context
import androidx.room.Room

/**
 * Created by john.wick on 2024/4/23 13:28.
 */
object Database {

    @Volatile
    private lateinit var database: AppDatabase

    @Synchronized
    fun init(context: Context) {
        if (::database.isInitialized) {
            return
        }

        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    fun getDatabase() = database
}