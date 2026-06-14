package com.example.allinonetester.utils

import android.util.Log
import com.example.allinonetester.config.AppConfig

object Logger {
    private const val TAG = "AllInOneTester"
    private var isDebug = true
    
    fun d(message: String) {
        if (isDebug) Log.d(TAG, message)
    }
    
    fun e(message: String, throwable: Throwable? = null) {
        Log.e(TAG, message, throwable)
    }
    
    fun i(message: String) {
        Log.i(TAG, message)
    }
}
