package com.example.myapplication.jetpack.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.ReadOnlyProperty

/**
 * Created by john.wick on 2024/4/24 13:35.
 */
object JetpackDataStore {

    private lateinit var dataStore: DataStore<Preferences>

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val viewModelCountKey by intPreferencesKey()
    private val livedataCountKey by intPreferencesKey()

    fun init(context: Context, name: String) {
        if (::dataStore.isInitialized) {
            return
        }

        dataStore = preferencesDataStore(name).getValue(context, String::length)
    }

    fun setViewModelCount(count: Int) = set(viewModelCountKey, count)

    fun getViewModelCount(defaultValue: Int = -1) = get(viewModelCountKey, defaultValue)

    fun setLiveDataCount(count: Int) = set(livedataCountKey, count)

    fun getLiveDataCount(defaultValue: Int = -1) = get(livedataCountKey, defaultValue)

    private fun <T> set(key: Preferences.Key<T>, value: T) = scope.launch {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private fun <T> get(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataStore.data.map { preferences -> preferences[key] ?: defaultValue }
}

fun <T> Flow<T>.await(): T = runBlocking { first() }

fun booleanPreferencesKey(name: String = "") =
    ReadOnlyProperty<Any, Preferences.Key<Boolean>> { _, property ->
        androidx.datastore.preferences.core.booleanPreferencesKey(name.ifEmpty { property.name })
    }

fun stringPreferencesKey(name: String = "") =
    ReadOnlyProperty<Any, Preferences.Key<String>> { _, property ->
        androidx.datastore.preferences.core.stringPreferencesKey(name.ifEmpty { property.name })
    }

fun intPreferencesKey(name: String = "") =
    ReadOnlyProperty<Any, Preferences.Key<Int>> { _, property ->
        androidx.datastore.preferences.core.intPreferencesKey(name.ifEmpty { property.name })
    }

fun longPreferencesKey(name: String = "") =
    ReadOnlyProperty<Any, Preferences.Key<Long>> { _, property ->
        androidx.datastore.preferences.core.longPreferencesKey(name.ifEmpty { property.name })
    }

fun floatPreferencesKey(name: String = "") =
    ReadOnlyProperty<Any, Preferences.Key<Float>> { _, property ->
        androidx.datastore.preferences.core.floatPreferencesKey(name.ifEmpty { property.name })
    }

fun doublePreferencesKey(name: String = "") =
    ReadOnlyProperty<Any, Preferences.Key<Double>> { _, property ->
        androidx.datastore.preferences.core.doublePreferencesKey(name.ifEmpty { property.name })
    }

fun stringSetPreferencesKey(name: String = "") =
    ReadOnlyProperty<Any, Preferences.Key<Set<String>>> { _, property ->
        androidx.datastore.preferences.core.stringSetPreferencesKey(name.ifEmpty { property.name })
    }

fun byteArrayPreferencesKey(name: String = "") =
    ReadOnlyProperty<Any, Preferences.Key<ByteArray>> { _, property ->
        androidx.datastore.preferences.core.byteArrayPreferencesKey(name.ifEmpty { property.name })
    }