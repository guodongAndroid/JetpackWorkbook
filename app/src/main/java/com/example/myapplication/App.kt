package com.example.myapplication

import android.app.Application
import com.example.myapplication.jetpack.datastore.JetpackDataStore
import com.example.myapplication.jetpack.room.Database

/**
 * Created by john.wick on 2024/4/23 13:27.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Database.init(this)
        JetpackDataStore.init(this, "app")
    }
}