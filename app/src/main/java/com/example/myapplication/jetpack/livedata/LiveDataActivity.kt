package com.example.myapplication.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLivedataBinding
import kotlin.concurrent.thread

/**
 * Created by john.wick on 2024/4/23 11:10.
 */
class LiveDataActivity : AppCompatActivity() {

    private val counterViewModel by viewModels<CounterViewModel> { CounterViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLivedataBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        counterViewModel.increment(10)

        counterViewModel.count.observe(this) { count ->
            binding.tvCount.text = count.toString()
        }

        binding.btnIncrement.setOnClickListener {
            counterViewModel.increment(1)
        }

        binding.btnDecrement.setOnClickListener {
            counterViewModel.decrement(2)
        }

        binding.btnIncrementPost.setOnClickListener {
            thread(name = "LiveData-PostIncrementThread") {
                counterViewModel.postIncrement(2)
            }
        }

        binding.btnDecrementPost.setOnClickListener {
            thread(name = "LiveData-PostDecrementThread") {
                counterViewModel.postDecrement(3)
            }
        }
    }
}