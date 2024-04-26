package com.example.myapplication.jetpack.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by john.wick on 2024/4/24 15:29.
 */
class CounterViewModelFactory(private val owner: LifecycleOwner) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CounterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CounterViewModel(owner) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}