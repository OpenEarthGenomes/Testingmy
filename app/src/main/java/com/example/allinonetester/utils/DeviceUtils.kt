package com.example.allinonetester.utils

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.os.StatFs
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.view.WindowManager
import java.io.File

object DeviceUtils {

    fun getBatteryInfo(context: Context): String {
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
        return "🔋 Akku: $percent% | 🌡️ Hőfok: ${temp}°C | 📊 Állapot: $status"
    }

    fun getNetworkState(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        val caps = cm.getNetworkCapabilities(activeNetwork)
        return when {
            caps == null -> "❌ Nincs aktív hálózat"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "📶 Wi-Fi kapcsolat"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "📱 Mobil adatkapcsolat"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "🔌 Ethernet"
            else -> "🌐 Egyéb hálózat"
        }
    }

    fun getRamInfo(): String {
        val mi = Runtime.getRuntime()
        val totalMB = mi.totalMemory() / (1024 * 1024)
        val freeMB = mi.freeMemory() / (1024 * 1024)
        val usedMB = totalMB - freeMB
        return "💾 RAM összes: ${totalMB} MB | Használt: ${usedMB} MB | Szabad: ${freeMB} MB"
    }

    fun getStorageInfo(): String {
        val path = android.os.Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong
        val totalMB = (totalBlocks * blockSize) / (1024 * 1024)
        val freeMB = (availableBlocks * blockSize) / (1024 * 1024)
        val usedMB = totalMB - freeMB
        return "💿 Belső tárhely: $totalMB MB | Használt: $usedMB MB | Szabad: $freeMB MB"
    }

    fun getScreenBrightness(context: Context): Int {
        return try {
            Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Exception) {
            -1
        }
    }

    fun getCpuCoreCount(): Int {
        return Runtime.getRuntime().availableProcessors()
    }

    fun listInstalledApps(context: Context, limit: Int = 20): List<String> {
        val packages = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return packages.take(limit).map { it.packageName }
    }

    fun getMobileNetworkType(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            when (tm.getDataNetworkType()) {
                TelephonyManager.NETWORK_TYPE_LTE -> "LTE (4G) 🚀"
                TelephonyManager.NETWORK_TYPE_NR -> "5G 🔥"
                TelephonyManager.NETWORK_TYPE_HSPAP -> "HSPA+"
                TelephonyManager.NETWORK_TYPE_EDGE -> "EDGE 🐢"
                else -> "Egyéb"
            }
        } else {
            "Nem lekérhető (API < 29)"
        }
    }

    fun getAvailableSensors(context: Context): List<String> {
        val sm = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        return sm.getSensorList(Sensor.TYPE_ALL).map {
            "${it.name} (${it.vendor})"
        }
    }

    fun getDisplayInfo(context: Context): String {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return "🖥️ Felbontás: ${dm.widthPixels}x${dm.heightPixels} | Sűrűség: ${dm.densityDpi} dpi"
    }

    fun getSecurityPatch(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            "🔒 Patch szint: ${Build.VERSION.SECURITY_PATCH}"
        } else "🔒 Nem támogatott (API < 23)"
    }

    fun isRooted(): Boolean {
        val paths = listOf(
            "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su",
            "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su",
            "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"
        )
        return paths.any { File(it).exists() } || try {
            Runtime.getRuntime().exec("which su").inputStream.read() != -1
        } catch (e: Exception) { false }
    }
}
