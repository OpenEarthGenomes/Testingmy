package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils

class PortScanUseCase {
    suspend operator fun invoke(host: String = "1.1.1.1", ports: List<Int> = listOf(80, 443, 53, 22, 8080)): String {
        return try {
            val open = NetworkUtils.scanPorts(host, ports)
            if (open.isEmpty()) {
                "Nincsenek nyitott portok a listából: $host 🔒"
            } else {
                "Nyitott portok $host:\n${open.joinToString(", ")} 🔓"
            }
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
