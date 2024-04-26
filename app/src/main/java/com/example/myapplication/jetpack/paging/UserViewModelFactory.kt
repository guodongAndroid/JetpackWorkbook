package com.example.myapplication.jetpack.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.jetpack.room.Database

/**
 * Created by john.wick on 2024/4/25 10:35.
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