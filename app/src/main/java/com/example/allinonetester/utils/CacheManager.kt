package com.example.allinonetester.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.allinonetester.config.AppConfig
import java.io.File

class CacheManager(private val context: Context? = null) {
    private val prefs: SharedPreferences? = context?.getSharedPreferences("app_cache", Context.MODE_PRIVATE)
    
    fun put(key: String, value: String) {
        prefs?.edit()?.putString(key, value)?.apply()
    }
    
    fun get(key: String): String? = prefs?.getString(key, null)
    
    fun clear() {
        prefs?.edit()?.clear()?.apply()
        context?.cacheDir?.deleteRecursively()
    }
    
    fun getCacheSize(): Long {
        return context?.cacheDir?.walkTopDown()?.filter { it.isFile }?.sumOf { it.length() } ?: 0
    }
}
