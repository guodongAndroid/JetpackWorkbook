package com.example.myapplication.jetpack.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.jetpack.datastore.JetpackDataStore
import com.example.myapplication.jetpack.datastore.await

/**
 * Created by john.wick on 2024/4/23 11:20.
 */
class CounterViewModel(owner: LifecycleOwner) : ViewModel() {

    private val _count = MutableLiveData(JetpackDataStore.getLiveDataCount(0).await())
    val count: LiveData<Int> = _count

    init {
        count.observe(owner) { count ->
            JetpackDataStore.setLiveDataCount(count)
        }
    }

    fun increment(step: Int = 1) {
        _count.value = _count.value?.plus(step)
    }

    fun decrement(step: Int = 1) {
        _count.value = _count.value?.minus(step)
    }

    fun postIncrement(step: Int = 1) {
        _count.postValue(_count.value?.plus(step))
    }

    fun postDecrement(step: Int = 1) {
        _count.postValue(_count.value?.minus(step))
    }
}