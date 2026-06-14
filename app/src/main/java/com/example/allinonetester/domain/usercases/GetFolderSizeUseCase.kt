package com.example.allinonetester.domain.usercases

import android.content.Context
import android.os.Build
import android.os.Environment
import com.example.allinonetester.utils.StorageUtils
import java.io.File
import javax.inject.Inject

class GetFolderSizeUseCase @Inject constructor(private val context: Context) {
    suspend operator fun invoke(path: String? = null): String {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                return "Hiba: MANAGE_EXTERNAL_STORAGE engedély hiányzik a teljes méretezéshez. Kérlek engedélyezd a beállításokban!"
            }

            val dir = path?.let { File(it) } ?: Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!dir.exists()) return "A mappa nem létezik: ${dir.absolutePath}"
            
            val size = StorageUtils.getFolderSize(dir)
            "Mappa: ${dir.absolutePath}\nTeljes méret: ${StorageUtils.formatSize(size)} 📁"
        } catch (e: Exception) {
            "Hiba a mappa olvasásakor: ${e.message}"
        }
    }
}
