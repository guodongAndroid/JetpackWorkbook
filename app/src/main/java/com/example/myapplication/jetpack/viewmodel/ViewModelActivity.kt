package com.example.myapplication.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityViewmodelBinding
import kotlinx.coroutines.launch

/**
 * Created by john.wick on 2024/4/23 9:37.
 */
class ViewModelActivity : AppCompatActivity() {

    private val counterViewModel: CounterViewModel by viewModels()
    private val eventViewModel by viewModels<EventViewModel> { EventViewModelFactory(counterViewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewmodelBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        lifecycleScope.launch {
            counterViewModel.count.collect { count ->
                binding.tvCount.text = count.toString()
            }
        }

        lifecycleScope.launch {
            eventViewModel.events.collect { count ->
                binding.tvCount.text = count.toString()
            }
        }

        binding.btnIncrement.setOnClickListener {
            counterViewModel.increment()
        }

        binding.btnDecrement.setOnClickListener {
            counterViewModel.decrement()
        }

        binding.btnIncrementEvent.setOnClickListener {
            eventViewModel.increment(2)
        }

        binding.btnDecrementEvent.setOnClickListener {
            eventViewModel.decrement(3)
        }
    }

}