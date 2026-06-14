package com.example.allinonetester.domain.usercases

import android.content.Context
import com.example.allinonetester.utils.NetworkUtils

class CheckInternetUseCase(private val context: Context) {
    suspend operator fun invoke(): String {
        return try {
            val isConnected = NetworkUtils.isInternetAvailable(context)
            "Internet elérhető: ${if (isConnected) "IGEN ✅" else "NEM ❌"}"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
