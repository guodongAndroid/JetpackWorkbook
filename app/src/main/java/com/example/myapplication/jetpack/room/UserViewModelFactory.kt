package com.example.myapplication.jetpack.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by john.wick on 2024/4/23 13:35.
 */
class UserViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(UserRepository(Database.getDatabase().userMapper())) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}