package com.example.myapplication.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityJetpackBinding

/**
 * Created by john.wick on 2024/4/23 9:27.
 */
class JetpackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJetpackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJetpackBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.container).navigateUp()
    }
}