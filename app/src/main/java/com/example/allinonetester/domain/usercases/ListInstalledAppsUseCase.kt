package com.example.allinonetester.domain.usercases

import android.content.Context
import com.example.allinonetester.utils.DeviceUtils

class ListInstalledAppsUseCase(private val context: Context) {
    suspend operator fun invoke(limit: Int = 20): String {
        val apps = DeviceUtils.listInstalledApps(context, limit)
        return "Telepített alkalmazások (első $limit):\n${apps.joinToString("\n")}"
    }
}
