package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils

class GetResponseTimeUseCase {
    suspend operator fun invoke(url: String = "https://clcoding.com"): String {
        return try {
            val timeMs = NetworkUtils.getResponseTime(url)
            "URL: $url\nVálaszidő: $timeMs ⏱️"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
