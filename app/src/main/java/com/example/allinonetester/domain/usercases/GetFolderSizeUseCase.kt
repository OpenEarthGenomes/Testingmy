package com.example.allinonetester.domain.usercases

import android.os.Environment
import com.example.allinonetester.utils.StorageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class GetFolderSizeUseCase @Inject constructor() {

    /**
     * Visszaadja a Downloads mappa méretét olvasható szövegként.
     * Nem vár paramétert, nem ad vissza Long-ot, hanem rögtön formázott stringet.
     */
    suspend operator fun invoke(): String = withContext(Dispatchers.IO) {
        try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists() || !downloadsDir.isDirectory) {
                return@withContext "❌ Downloads mappa nem elérhető."
            }
            val sizeBytes = calculateSize(downloadsDir)
            val sizeFormatted = formatSize(sizeBytes)  // saját formázó, mert a StorageUtils-ban nincs
            return@withContext "📁 Downloads mappa mérete: $sizeFormatted"
        } catch (e: Exception) {
            return@withContext "Hiba a mappa méretének számolásakor: ${e.message}"
        }
    }

    private fun calculateSize(directory: File): Long {
        var total = 0L
        val files = directory.listFiles() ?: return 0L
        for (file in files) {
            total += if (file.isFile) file.length() else calculateSize(file)
        }
        return total
    }

    private fun formatSize(bytes: Long): String {
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
