package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PingUseCase @Inject constructor() {
    suspend operator fun invoke(host: String = "1.1.1.1"): String = withContext(Dispatchers.IO) {
        try {
            val result = NetworkUtils.ping(host)
            "Ping $host:\n$result 📡"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
