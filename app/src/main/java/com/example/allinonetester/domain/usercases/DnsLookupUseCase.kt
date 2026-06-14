package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.NetworkUtils

class DnsLookupUseCase {
    suspend operator fun invoke(domain: String = "clcoding.com"): String {
        return try {
            val ip = NetworkUtils.dnsLookup(domain)
            "Domain: $domain → IP: $ip"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
