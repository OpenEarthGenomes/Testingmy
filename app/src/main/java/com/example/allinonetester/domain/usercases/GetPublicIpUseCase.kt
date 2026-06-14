package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils

class GetPublicIpUseCase {
    suspend operator fun invoke(): String {
        return try {
            val ip = NetworkUtils.getPublicIp()
            "Publikus IP cím: $ip 🌐"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
