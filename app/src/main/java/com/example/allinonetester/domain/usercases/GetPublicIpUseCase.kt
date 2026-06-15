package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPublicIpUseCase @Inject constructor() {
    suspend operator fun invoke(): String = withContext(Dispatchers.IO) {
        try {
            val ip = NetworkUtils.getPublicIp()
            "Publikus IP cím: $ip 🌐"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
