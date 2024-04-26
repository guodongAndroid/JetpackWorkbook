package com.example.myapplication.jetpack.lifecycle

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.lang.IllegalStateException

/**
 * Created by john.wick on 2024/4/24 17:31.
 */
class LifecycleComponent(private val context: Context) : LifecycleEventObserver {

    companion object {
        private const val TAG = "LifecycleComponent"
    }

    private fun onCreate() {
        Toast.makeText(context, "${TAG}:onCreate", Toast.LENGTH_SHORT).show()
    }

    private fun onStart() {
        Toast.makeText(context, "${TAG}:onStart", Toast.LENGTH_SHORT).show()
    }

    private fun onResume() {
        Toast.makeText(context, "${TAG}:onResume", Toast.LENGTH_SHORT).show()
    }

    private fun onPause() {
        Toast.makeText(context, "${TAG}:onPause", Toast.LENGTH_SHORT).show()
    }

    private fun onStop() {
        Toast.makeText(context, "${TAG}:onStop", Toast.LENGTH_SHORT).show()
    }

    private fun onDestroy() {
        Toast.makeText(context, "${TAG}:onDestroy", Toast.LENGTH_SHORT).show()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_CREATE -> onCreate()
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE -> onPause()
            Lifecycle.Event.ON_STOP -> onStop()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            Lifecycle.Event.ON_ANY -> throw IllegalStateException()
        }
    }
}