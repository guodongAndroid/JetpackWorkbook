package com.example.myapplication.jetpack.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Created by john.wick on 2024/4/23 13:22.
 */
@Dao
interface UserMapper {
    @Insert
    fun insert(user: User): Long

    @Update
    fun update(user: User): Int

    @Delete
    fun delete(user: User): Int

    @Query("select * from t_user where id = :userId")
    fun getUserById(userId: Int): User?

    @Query("select * from t_user")
    fun getAllUser(): List<User>

    @Query("select * from t_user order by id limit :size offset ((:page - 1) * :size)")
    fun getUsers(page: Int, size: Int): List<User>

    @Query("select * from t_user order by id")
    fun getUsersWithPagingSource(): PagingSource<Int, User>
}