package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PortScanUseCase @Inject constructor() {
    suspend operator fun invoke(host: String = "1.1.1.1", ports: List<Int> = listOf(80, 443, 53, 22, 8080)): String = withContext(Dispatchers.IO) {
        try {
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
