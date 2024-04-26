package com.example.myapplication.jetpack.paging

import com.example.myapplication.jetpack.room.UserMapper

/**
 * Created by john.wick on 2024/4/25 10:06.
 */
class UserRepository(private val userMapper: UserMapper) {
    fun userPagingSource(useRoom: Boolean) =
        if (useRoom) userMapper.getUsersWithPagingSource() else UserPagingSource(userMapper)
}