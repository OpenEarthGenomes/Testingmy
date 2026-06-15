package com.example.allinonetester.domain.usercases

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ListInstalledAppsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): String {
        return try {
            val pm = context.packageManager
            val apps = pm.getInstalledPackages(PackageManager.GET_META_DATA)
            val userApps = apps.filter { 
                (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0 
            }
            
            if (userApps.isEmpty()) {
                "Nincsenek telepített harmadik féltől származó alkalmazások."
            } else {
                userApps.joinToString("\n") { 
                    it.applicationInfo.loadLabel(pm).toString() + " (${it.packageName})" 
                }
            }
        } catch (e: Exception) {
            "Hiba az alkalmazások listázása során"
        }
    }
}

