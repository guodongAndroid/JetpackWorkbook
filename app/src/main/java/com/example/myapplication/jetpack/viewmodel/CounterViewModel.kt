package com.example.myapplication.jetpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.jetpack.datastore.JetpackDataStore
import com.example.myapplication.jetpack.datastore.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by john.wick on 2024/4/23 10:08.
 */
class CounterViewModel : ViewModel() {

    private val _count = MutableStateFlow(JetpackDataStore.getViewModelCount(0).await())
    val count: StateFlow<Int> = _count

    init {
        viewModelScope.launch {
            count.collect {
                JetpackDataStore.setViewModelCount(it)
            }
        }
    }

    fun increment() {
        _count.value++
    }

    fun decrement() {
        _count.value--
    }
}