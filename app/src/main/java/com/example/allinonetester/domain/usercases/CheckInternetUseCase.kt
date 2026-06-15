package com.example.allinonetester.domain.usercases

import android.content.Context
import com.example.allinonetester.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckInternetUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): String {
        return try {
            val isConnected = NetworkUtils.isInternetAvailable(context)
            "Internet elérhető: ${if (isConnected) "IGEN ✅" else "NEM ❌"}"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
