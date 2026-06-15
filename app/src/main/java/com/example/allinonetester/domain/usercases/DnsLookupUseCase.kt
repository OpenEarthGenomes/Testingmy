package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DnsLookupUseCase @Inject constructor() {
    suspend operator fun invoke(domain: String = "clcoding.com"): String = withContext(Dispatchers.IO) {
        try {
            val ip = NetworkUtils.dnsLookup(domain)
            "Domain: $domain → IP: $ip"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
