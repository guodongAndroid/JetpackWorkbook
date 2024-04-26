package com.example.myapplication.jetpack.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.databinding.ActivityLifecycleBinding
import com.example.myapplication.jetpack.room.RoomActivity
import kotlinx.coroutines.launch

/**
 * Created by john.wick on 2024/4/24 15:53.
 */
class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLifecycleBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                val state = when (event) {
                    Lifecycle.Event.ON_CREATE -> "OnCreate"
                    Lifecycle.Event.ON_START -> "OnStart"
                    Lifecycle.Event.ON_RESUME -> "OnResume"
                    Lifecycle.Event.ON_PAUSE -> {
                        Toast.makeText(this@LifecycleActivity, "OnPause", Toast.LENGTH_SHORT).show()
                        "OnPause"
                    }
                    Lifecycle.Event.ON_STOP -> "OnStop"
                    Lifecycle.Event.ON_DESTROY -> "OnDestroy"
                    Lifecycle.Event.ON_ANY ->
                        throw IllegalArgumentException("ON_ANY must not been send by anybody")
                }

                binding.tvLifeState.text = state
            }
        })

        lifecycle.addObserver(LifecycleComponent(this))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Toast.makeText(this@LifecycleActivity, "OnResumed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLifeState.setOnClickListener {
            AlertDialog.Builder(this).setMessage("弹窗").show()
        }

        binding.btnNavigateToRoom.setOnClickListener {
            RoomActivity.start(this)
        }
    }
}