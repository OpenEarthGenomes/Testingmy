package com.example.allinonetester.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.app.ActivityManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.view.WindowManager
import java.io.File

object DeviceUtils {

    fun getBatteryInfo(context: Context): String {
        return try {
            val batteryIntent = context.registerReceiver(null, android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED))
            val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
            val percent = if (level != -1 && scale != -1) (level * 100 / scale) else -1
            val temp = (batteryIntent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0) / 10.0
            val status = when (batteryIntent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                BatteryManager.BATTERY_STATUS_CHARGING -> "Töltés ⚡"
                BatteryManager.BATTERY_STATUS_DISCHARGING -> "Kisütés 🔋"
                BatteryManager.BATTERY_STATUS_FULL -> "Tele ✅"
                else -> "Ismeretlen"
            }
            "🔋 Akku: $percent% | 🌡️ Hőfok: ${temp}°C | 📊 Állapot: $status"
        } catch (e: Exception) {
            "🔋 Akku: Nem elérhető"
        }
    }

    // Biztonságos, összeomlásmentes mobilhálózat-típus lekérdezés
    fun getNetworkState(context: Context): String {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetwork ?: return "❌ Nincs aktív hálózat"
            val caps = cm.getNetworkCapabilities(activeNetwork) ?: return "❌ Nincs hálózat"
            
            when {
                caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "📶 Wi-Fi kapcsolat"
                caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    
                    // Verziókezelés a sárga hibák ellen és az összeomlás megelőzésére
                    val networkType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        try {
                            tm.dataNetworkType
                        } catch (se: SecurityException) {
                            TelephonyManager.NETWORK_TYPE_UNKNOWN
                        }
                    } else {
                        @Suppress("DEPRECATION")
                        tm.networkType
                    }

                    val typeString = when (networkType) {
                        TelephonyManager.NETWORK_TYPE_GPRS,
                        TelephonyManager.NETWORK_TYPE_EDGE,
                        TelephonyManager.NETWORK_TYPE_CDMA,
                        TelephonyManager.NETWORK_TYPE_1xRTT,
                        TelephonyManager.NETWORK_TYPE_IDEN -> "2G"
                        
                        TelephonyManager.NETWORK_TYPE_UMTS,
                        TelephonyManager.NETWORK_TYPE_EVDO_0,
                        TelephonyManager.NETWORK_TYPE_EVDO_A,
                        TelephonyManager.NETWORK_TYPE_HSDPA,
                        TelephonyManager.NETWORK_TYPE_HSUPA,
                        TelephonyManager.NETWORK_TYPE_HSPA,
                        TelephonyManager.NETWORK_TYPE_EVDO_B,
                        TelephonyManager.NETWORK_TYPE_EHRPD,
                        TelephonyManager.NETWORK_TYPE_HSPAP -> "3G"
                        
                        TelephonyManager.NETWORK_TYPE_LTE -> "4G (LTE)"
                        TelephonyManager.NETWORK_TYPE_NR -> "5G"
                        else -> "Mobil adat (Típus korlátozott)"
                    }
                    "📱 Mobil hálózat: $typeString"
                }
                else -> "🌐 Egyéb hálózat"
            }
        } catch (e: SecurityException) {
            "❌ Hiányzó engedély (READ_PHONE_STATE)"
        } catch (e: Exception) {
            "❌ Hiba: ${e.message}"
        }
    }

    fun getRamInfo(context: Context): String {
        return try {
            val mi = ActivityManager.MemoryInfo()
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.getMemoryInfo(mi)
            val freeMB = mi.availMem / (1024 * 1024)
            "💾 Szabad RAM: $freeMB MB"
        } catch (e: Exception) {
            "💾 RAM: Nem elérhető"
        }
    }

    // Sárga figyelmeztetés javítva verziókezeléssel
    fun getInstalledAppNames(context: Context): List<String> {
        return try {
            val pm = context.packageManager
            val packages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getInstalledPackages(PackageManager.PackageInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
            } else {
                @Suppress("DEPRECATION")
                pm.getInstalledPackages(PackageManager.GET_META_DATA)
            }
            packages.mapNotNull { pm.getApplicationLabel(it.applicationInfo).toString() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getScreenBrightness(context: Context): Int {
        return try {
            Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Exception) { -1 }
    }

    fun getCpuCoreCount(): Int = Runtime.getRuntime().availableProcessors()

    // Sárga figyelmeztetés javítva az új WindowMetrics API használatával
    fun getDisplayInfo(context: Context): String {
        return try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.display?.getRealMetrics(dm)
            } else {
                @Suppress("DEPRECATION")
                wm.defaultDisplay.getMetrics(dm)
            }
            "🖥️ ${dm.widthPixels}x${dm.heightPixels} dpi: ${dm.densityDpi}"
        } catch (e: Exception) {
            "🖥️ Kijelző információ nem elérhető"
        }
    }

    fun getSecurityPatch(): String = "🔒 Patch: ${Build.VERSION.SECURITY_PATCH}"

    fun isRooted(): Boolean {
        val paths = listOf("/system/xbin/su", "/system/bin/su")
        return paths.any { File(it).exists() }
    }
}
