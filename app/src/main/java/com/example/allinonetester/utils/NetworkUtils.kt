package com.example.allinonetester.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL
import kotlin.system.measureTimeMillis

object NetworkUtils {

    fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun dnsLookup(host: String): String {
        return try {
            val addresses = InetAddress.getAllByName(host)
            // Javítva: Garantáltan non-null Stringet adunk vissza a típus-illesztési hiba ellen
            addresses.firstOrNull()?.hostAddress ?: "Nem található IP cím"
        } catch (e: Exception) {
            "DNS hiba: ${e.message ?: "Ismeretlen hiba"}"
        }
    }

    fun getPublicIp(): String {
        return try {
            URL("https://api.ipify.org").readText()
        } catch (e: Exception) {
            "Hiba az IP lekérésekor"
        }
    }

    fun getWebsiteResponseTime(urlString: String): String {
        return try {
            val url = URL(urlString)
            val time = measureTimeMillis {
                val connection = url.openConnection()
                connection.connectTimeout = 5000
                connection.connect()
            }
            "$time ms"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }

    fun ping(host: String, timeoutMs: Int = 3000): String {
        return try {
            val address = InetAddress.getByName(host)
            // Javítva: A timeoutMs paraméter most már ténylegesen fel van használva
            val reachable = address.isReachable(timeoutMs)
            if (reachable) "Ping sikeres ide: $host" else "Időtúllépés ($timeoutMs ms)"
        } catch (e: Exception) {
            // Javítva: Biztosítjuk, hogy az e.message ne lehessen null (CharSequence elvárás miatt)
            val errorMsg: String = e.message ?: "Ismeretlen hiba"
            "Ping hiba: $errorMsg"
        }
    }

    fun scanPorts(host: String, ports: List<Int>, timeoutMs: Int = 500): List<Int> {
        val openPorts = mutableListOf<Int>()
        for (port in ports) {
            try {
                val socket = Socket()
                socket.connect(InetSocketAddress(host, port), timeoutMs)
                socket.close()
                openPorts.add(port)
            } catch (e: Exception) {
                // A port zárva van vagy időtúllépés történt
            }
        }
        return openPorts
    }
}
