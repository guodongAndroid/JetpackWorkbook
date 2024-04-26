package com.example.myapplication.jetpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * Created by john.wick on 2024/4/23 10:34.
 */
class EventViewModel(private val counterViewModel: CounterViewModel) : ViewModel() {

    private val _events = MutableSharedFlow<Int>()
    val events: SharedFlow<Int> = _events

    private var count: Int = counterViewModel.count.value

    init {
        viewModelScope.launch {
            counterViewModel.count.collect {
                count = it
            }
        }
    }

    fun increment(step: Int) {
        viewModelScope.launch {
            count += step
            _events.emit(count)
        }
    }

    fun decrement(step: Int) {
        viewModelScope.launch {
            count -= step
            _events.emit(count)
        }
    }
}