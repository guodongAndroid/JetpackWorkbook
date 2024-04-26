package com.example.myapplication.jetpack.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by john.wick on 2024/4/23 13:35.
 */
class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers

    private val _state = MutableStateFlow(false)
    val state: StateFlow<Boolean> = _state

    init {
        loadAllUser()
    }

    suspend fun insert(user: User): Long = userRepository.insert(user)

    suspend fun update(user: User): Int = userRepository.update(user)

    suspend fun delete(user: User): Int = userRepository.delete(user)

    suspend fun getUserById(userId: Int): User? = userRepository.getUserById(userId)

    fun loadAllUser(): Job = viewModelScope.launch {
        _state.value = true
        delay(1000 * 3)
        try {
            _allUsers.value = userRepository.loadAllUser()
            _state.value = false
        } catch (e: Exception) {
            _state.value = false
        }
    }
}