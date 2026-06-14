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

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun dnsLookup(host: String): String {
        return try {
            val addresses = InetAddress.getAllByName(host)
            // JAVÍTVA: Garantáltan non-null Stringet adunk vissza a típus-illesztési sárga hiba ellen
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

    fun getResponseTime(urlString: String): String {
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
            // JAVÍTVA: A timeoutMs paraméter most már ténylegesen fel van használva
            val reachable = address.isReachable(timeoutMs)
            if (reachable) "Ping sikeres ide: $host" else "Időtúllépés ($timeoutMs ms)"
        } catch (e: Exception) {
            // JAVÍTVA: Biztosítjuk, hogy az e.message ne lehessen null (CharSequence elvárás miatt)
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

    fun simpleDownloadSpeed(): String {
        return try {
            val startTime = System.currentTimeMillis()
            val url = URL("https://speed.cloudflare.com/__down?bytes=1048576") // 1MB tesztfájl letöltése
            val connection = url.openConnection()
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.inputStream.readBytes()
            val endTime = System.currentTimeMillis()
            val timeTaken = (endTime - startTime) / 1000.0 // idő másodpercben
            val speedMbps = (1.0 * 8) / timeTaken // 1MB = 8 Megabits
            String.format("%.2f Mbps", speedMbps)
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
