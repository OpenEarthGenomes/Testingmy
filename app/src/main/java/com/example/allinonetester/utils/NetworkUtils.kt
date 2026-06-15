package com.example.allinonetester.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

object NetworkUtils {

    // Biztonságos OkHttpClient időtúllépési beállításokkal
    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build()

    // Suspend függvény, hogy ne blokkolja a fő UI szálat
    suspend fun getResponseTime(url: String): String = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(url)
            .build()

        try {
            val startTime = System.currentTimeMillis()
            
            client.newCall(request).execute().use { response ->
                val endTime = System.currentTimeMillis()
                
                if (response.isSuccessful) {
                    "${endTime - startTime} ms"
                } else {
                    "Hiba kód: ${response.code}"
                }
            }
        } catch (e: IOException) {
            "Időtúllépés vagy nincs hálózat"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
