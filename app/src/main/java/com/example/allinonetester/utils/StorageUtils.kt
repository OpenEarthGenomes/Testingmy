package com.example.allinonetester.utils

import java.io.File

object StorageUtils {

    fun getFolderSize(file: File): Long {
        if (!file.exists()) return 0
        if (file.isFile) return file.length()
        
        var size: Long = 0
        try {
            val files = file.listFiles()
            if (files != null) {
                for (child in files) {
                    size += getFolderSize(child)
                }
            }
        } catch (e: SecurityException) {
            // Hozzáférés megtagadva egy specifikus mappához, átugorjuk
        }
        return size
    }

    fun formatSize(bytes: Long): String {
        val units = listOf("B", "KB", "MB", "GB", "TB")
        var size = bytes.toDouble()
        var unitIndex = 0
        while (size >= 1024 && unitIndex < units.lastIndex) {
            size /= 1024
            unitIndex++
        }
        return "%.2f %s".format(size, units[unitIndex])
    }
}
