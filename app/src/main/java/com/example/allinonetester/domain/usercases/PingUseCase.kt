package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils

class PingUseCase {
    suspend operator fun invoke(host: String = "1.1.1.1"): String {
        return try {
            val result = NetworkUtils.ping(host)
            "Ping $host:\n$result 📡"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
