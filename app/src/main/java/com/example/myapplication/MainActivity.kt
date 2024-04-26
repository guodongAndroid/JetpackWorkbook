package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.tvProgress.text = binding.runway.progress.toString()

        binding.seekbar.progress = binding.runway.progress
        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.runway.progress = progress % 401
                binding.runway.slope = progress * 1.0F / 15
                binding.runway.speed = progress * 1.0F / 30
//                binding.runway.laps = floor(progress * 1.0 / 10).toInt()
                binding.tvProgress.text = (progress % 400).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.btnReset.setOnClickListener {
            binding.seekbar.progress = 0
            binding.runway.reset()
        }
    }
}