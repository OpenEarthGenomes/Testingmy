package com.example.allinonetester.domain.usercases

import android.os.Environment
import com.example.allinonetester.utils.StorageUtils
import java.io.File

class GetFolderSizeUseCase {
    suspend operator fun invoke(path: String? = null): String {
        return try {
            val dir = path?.let { File(it) } ?: Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!dir.exists()) return "A mappa nem létezik: ${dir.absolutePath}"
            val size = StorageUtils.getFolderSize(dir)
            "Mappa: ${dir.absolutePath}\nMéret: ${StorageUtils.formatSize(size)} 📁"
        } catch (e: Exception) {
            "Hiba: ${e.message}"
        }
    }
}
