package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils

class SpeedTestUseCase {
    suspend operator fun invoke(): String {
        return try {
            val speedMbps = NetworkUtils.simpleDownloadSpeed()
            "Letöltési sebesség: %.2f Mbps 🚀".format(speedMbps)
        } catch (e: Exception) {
            "Sebességteszt hiba: ${e.message}"
        }
    }
}
