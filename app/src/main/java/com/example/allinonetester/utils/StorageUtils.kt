package com.example.allinonetester.utils

import android.content.Context
import android.os.Environment
import android.os.StatFs
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getFreeInternalSpace(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return availableBlocks * blockSize
    }

    fun getTotalInternalSpace(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        return totalBlocks * blockSize
    }

    fun getStorageInfo(): String {
        val freeMB = getFreeInternalSpace() / (1024 * 1024)
        val totalMB = getTotalInternalSpace() / (1024 * 1024)
        return "💾 Tárhely: $freeMB MB szabad / $totalMB MB összesen"
    }

    fun getAppCacheDirectory(): File {
        return context.cacheDir
    }

    fun getAppExternalCacheDirectory(): File? {
        return context.externalCacheDir
    }

    fun deleteDirectoryContents(directory: File): Boolean {
        if (!directory.exists() || !directory.isDirectory) return false
        
        val files = directory.listFiles() ?: return true
        var success = true
        
        for (file in files) {
            success = if (file.isDirectory) {
                success && deleteDirectoryContents(file) && file.delete()
            } else {
                success && file.delete()
            }
        }
        return success
    }
}
