package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpeedTestUseCase @Inject constructor() {
    suspend operator fun invoke(): String = withContext(Dispatchers.IO) {
        try {
            val speedMbps = NetworkUtils.simpleDownloadSpeed()
            "Letöltési sebesség: %.2f Mbps 🚀".format(speedMbps)
        } catch (e: Exception) {
            "Sebességteszt hiba: ${e.message}"
        }
    }
}
