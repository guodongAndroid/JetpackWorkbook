package com.example.myapplication.jetpack.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by john.wick on 2024/4/23 13:19.
 */
@Entity(tableName = "t_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val name: String,
    val age: Int
)
