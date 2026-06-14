package com.example.allinonetester.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import java.io.File

object DeviceUtils {

    fun getBatteryStatus(context: Context): String {
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, ifilter)
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val pct = if (level >= 0 && scale > 0) (level * 100 / scale.toFloat()).toInt() else 0
        return "$pct%"
    }

    fun getRamUsage(context: Context): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        val total = memoryInfo.totalMem / (1024 * 1024)
        val avail = memoryInfo.availMem / (1024 * 1024)
        val used = total - avail
        return "Használt: $used MB / Összes: $total MB"
    }

    fun getStorageInfo(): String {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        val totalBlocks = stat.blockCountLong
        val total = (totalBlocks * blockSize) / (1024 * 1024 * 1024)
        val avail = (availableBlocks * blockSize) / (1024 * 1024 * 1024)
        return "Szabad: $avail GB / Összes: $total GB"
    }

    fun getDeviceModel(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE}, API ${Build.VERSION.SDK_INT})"
    }

    fun getCpuCores(): Int {
        return Runtime.getRuntime().availableProcessors()
    }

    fun getScreenBrightness(context: Context): String {
        return try {
            val brightness = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
            "$brightness / 255"
        } catch (e: Exception) {
            "Nem elérhető"
        }
    }

    fun getInstalledAppsCount(context: Context): Int {
        return try {
            val pm = context.packageManager
            val apps = pm.getInstalledPackages(PackageManager.GET_META_DATA)
            apps.size
        } catch (e: Exception) {
            0
        }
    }

    fun getSensorsList(context: Context): List<String> {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        return sensorList.map { it.name }
    }

    // Modernizált felbontás lekérdezés, ami javítja a régi API miatti sárga logokat
    fun getDisplayResolution(context: Context): String {
        val metrics = context.resources.displayMetrics
        return "${metrics.widthPixels}x${metrics.heightPixels} Px"
    }

    fun getSecurityPatch(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Build.VERSION.SECURITY_PATCH
        } else {
            "Nem elérhető"
        }
    }

    fun isRooted(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        for (path in paths) {
            if (File(path).exists()) return true
        }
        return false
    }
}
