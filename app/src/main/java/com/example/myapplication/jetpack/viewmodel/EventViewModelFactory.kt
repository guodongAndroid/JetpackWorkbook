package com.example.myapplication.jetpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by john.wick on 2024/4/23 10:54.
 */
class EventViewModelFactory(private val counterViewModel: CounterViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventViewModel(counterViewModel) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}