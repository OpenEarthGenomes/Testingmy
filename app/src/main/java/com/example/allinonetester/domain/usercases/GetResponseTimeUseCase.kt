package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetResponseTimeUseCase @Inject constructor() {
    suspend operator fun invoke(url: String = "https://clcoding.com"): String = withContext(Dispatchers.IO) {
        try {
            val timeMs = NetworkUtils.getResponseTime(url)
            "URL: $url\nVálaszidő: $timeMs ⏱️"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
