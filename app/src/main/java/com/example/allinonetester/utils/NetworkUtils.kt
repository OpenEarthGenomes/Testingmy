package com.example.allinonetester.utils

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.TimeUnit

object NetworkUtils {

    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build()

    // Golyóálló hálózattípus lekérdező: Sosem omlik össze!
    fun getDetailedNetworkType(context: Context): String {
        return try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return "Nincs kapcsolat"
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return "Ismeretlen hálózat"

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return "Wi-Fi"
            }

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                // Ellenőrizzük, hogy a felhasználó megadta-e a futásidejű engedélyt a pontos típus (4G/5G) lekéréséhez
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    when (telephonyManager.dataNetworkType) {
                        TelephonyManager.NETWORK_TYPE_NR -> "Mobiladat (5G)"
                        TelephonyManager.NETWORK_TYPE_LTE -> "Mobiladat (4G)"
                        TelephonyManager.NETWORK_TYPE_HSPAP,
                        TelephonyManager.NETWORK_TYPE_UMTS -> "Mobiladat (3G)"
                        TelephonyManager.NETWORK_TYPE_EDGE,
                        TelephonyManager.NETWORK_TYPE_GPRS -> "Mobiladat (2G)"
                        else -> "Mobiladat (Ismeretlen generáció)"
                    }
                } else {
                    return "Mobiladat (Pontos típushoz engedély kell)"
                }
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return "Ethernet"
            } else {
                return "Egyéb hálózat"
            }
        } catch (e: SecurityException) {
            "Mobiladat (Biztonsági korlátozás)"
        } catch (e: Exception) {
            "Hiba a lekérdezéskor"
        }
    }

    suspend fun getResponseTime(url: String): String = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url).build()
        try {
            val startTime = System.currentTimeMillis()
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    "${System.currentTimeMillis() - startTime} ms"
                } else {
                    "Hiba kód: ${response.code}"
                }
            }
        } catch (e: Exception) {
            "Időtúllépés/Nincs kapcsolat"
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    suspend fun ping(host: String): String = withContext(Dispatchers.IO) {
        try {
            val process = Runtime.getRuntime().exec("/system/bin/ping -c 3 $host")
            val exitValue = process.waitFor()
            if (exitValue == 0) "Sikeres (0% csomagveszteség)" else "Sikertelen vagy letiltott ICMP"
        } catch (e: Exception) {
            "Hiba a ping során"
        }
    }

    suspend fun simpleDownloadSpeed(): Double = withContext(Dispatchers.IO) {
        val url = "https://speed.hetzner.de/100MB.bin"
        val request = Request.Builder().url(url).build()
        try {
            val startTime = System.currentTimeMillis()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext 0.0
                val body = response.body ?: return@withContext 0.0
                val source = body.source()
                var totalBytes = 0L
                val buffer = okio.Buffer()
                
                while (System.currentTimeMillis() - startTime < 2000) {
                    val read = source.read(buffer, 8192)
                    if (read == -1L) break
                    totalBytes += read
                    buffer.clear()
                }
                val timeInSeconds = (System.currentTimeMillis() - startTime) / 1000.0
                val bits = totalBytes * 8
                val megabits = bits / 1_000_000.0
                if (timeInSeconds > 0) megabits / timeInSeconds else 0.0
            }
        } catch (e: Exception) {
            0.0
        }
    }

    suspend fun scanPorts(host: String, ports: List<Int>): List<Int> = withContext(Dispatchers.IO) {
        val openPorts = mutableListOf<Int>()
        for (port in ports) {
            try {
                val socket = Socket()
                socket.connect(InetSocketAddress(host, port), 500)
                socket.close()
                openPorts.add(port)
            } catch (e: Exception) {
                // Port zárva vagy timeout
            }
        }
        openPorts
    }

    suspend fun dnsLookup(domain: String): String = withContext(Dispatchers.IO) {
        try {
            val inetAddress = InetAddress.getByName(domain)
            inetAddress.hostAddress ?: "Nem található"
        } catch (e: Exception) {
            "Hiba a DNS feloldásakor"
        }
    }

    suspend fun getPublicIp(): String = withContext(Dispatchers.IO) {
        val request = Request.Builder().url("https://api.ipify.org").build()
        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) response.body?.string() ?: "Ismeretlen" else "API Hiba"
            }
        } catch (e: Exception) {
            "Nincs kapcsolat"
        }
    }
}

