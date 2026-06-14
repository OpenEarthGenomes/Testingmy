package com.example.allinonetester.domain.usercases

import android.app.ActivityManager
import android.content.Context
import javax.inject.Inject

class ClearRamUseCase @Inject constructor(
    private val context: Context
) {
    operator fun invoke(): Long {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        
        val memoryInfoBefore = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfoBefore)
        val availableBefore = memoryInfoBefore.availMem

        val runningProcesses = activityManager.runningAppProcesses
        if (runningProcesses != null) {
            for (processInfo in runningProcesses) {
                if (processInfo.pkgList != null) {
                    for (pkg in processInfo.pkgList) {
                        if (pkg != context.packageName) {
                            activityManager.killBackgroundProcesses(pkg)
                        }
                    }
                }
            }
        }

        val memoryInfoAfter = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfoAfter)
        val availableAfter = memoryInfoAfter.availMem

        val freedBytes = availableAfter - availableBefore
        return if (freedBytes > 0) freedBytes / (1024 * 1024) else 0L
    }
}

