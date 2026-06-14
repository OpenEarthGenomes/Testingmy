package com.example.allinonetester.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.Socket
import java.net.URL
import java.util.concurrent.TimeUnit

object NetworkUtils {

    fun isInternetAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun dnsLookup(domain: String): String {
        return InetAddress.getByName(domain).hostAddress
    }

    fun getPublicIp(): String {
        return URL("https://api.ipify.org").readText()
    }

    fun getResponseTime(urlString: String): Pair<Int, Long> {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder().url(urlString).build()
        val start = System.currentTimeMillis()
        val response = client.newCall(request).execute()
        val duration = System.currentTimeMillis() - start
        return Pair(response.code, duration)
    }

    fun simpleDownloadSpeed(testUrl: String = "https://speed.cloudflare.com/__down?bytes=5000000", bytesToDownload: Long = 5_000_000): Double {
        val url = URL(testUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 5000
        connection.readTimeout = 10000
        val start = System.currentTimeMillis()
        var bytesRead = 0L
        connection.inputStream.use { input ->
            val buffer = ByteArray(8192)
            var len = 0
            while (bytesRead < bytesToDownload && input.read(buffer).also { len = it } != -1) {
                bytesRead += len
            }
        }
        val durationSec = (System.currentTimeMillis() - start) / 1000.0
        val megabits = (bytesRead * 8) / 1_000_000.0
        return megabits / durationSec
    }

    fun ping(host: String, timeoutMs: Int = 3000): String {
        return try {
            val reachable = InetAddress.getByName(host).isReachable(timeoutMs)
            if (reachable) "Válaszol ✅" else "Nem válaszol ❌"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }

    fun scanPorts(host: String, ports: List<Int>, timeoutMs: Int = 500): List<Int> {
        return ports.filter { port ->
            try {
                Socket(host, port).close()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    fun listNetworkInterfaces(): List<String> {
        return NetworkInterface.getNetworkInterfaces().toList().map { iface ->
            val ips = iface.inetAddresses.toList().joinToString(", ") { it.hostAddress }
            "${iface.displayName} (${iface.name}) : $ips"
        }
    }
}



