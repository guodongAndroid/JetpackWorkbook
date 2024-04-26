package com.example.myapplication.jetpack.room

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by john.wick on 2024/4/23 13:41.
 */
class UserRepository(private val userMapper: UserMapper) {

    suspend fun insert(user: User) = withContext(Dispatchers.IO) {
        userMapper.insert(user)
    }

    suspend fun update(user: User) = withContext(Dispatchers.IO) {
        userMapper.update(user)
    }

    suspend fun delete(user: User) = withContext(Dispatchers.IO) {
        userMapper.delete(user)
    }

    suspend fun getUserById(userId: Int) = withContext(Dispatchers.IO) {
        userMapper.getUserById(userId)
    }

    suspend fun loadAllUser() = withContext(Dispatchers.IO) {
        userMapper.getAllUser()
    }

    suspend fun getUsers(page: Int, size: Int) = withContext(Dispatchers.IO) {
        userMapper.getUsers(page, size)
    }
}