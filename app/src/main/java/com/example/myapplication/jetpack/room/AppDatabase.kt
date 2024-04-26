package com.example.myapplication.jetpack.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by john.wick on 2024/4/23 13:26.
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userMapper(): UserMapper
}