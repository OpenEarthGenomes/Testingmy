package com.example.allinonetester.domain.usercases

import android.os.Environment
import com.example.allinonetester.utils.StorageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFolderSizeUseCase @Inject constructor() {

    /**
     * Visszaadja a Downloads mappa méretét olvasható formában.
     * Nem vár paramétert – a Downloads mappát használja alapértelmezetten.
     */
    suspend operator fun invoke(): String = withContext(Dispatchers.IO) {
        try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists() || !downloadsDir.isDirectory) {
                return@withContext "❌ A Downloads mappa nem elérhető."
            }
            val sizeBytes = calculateSize(downloadsDir)
            val sizeFormatted = StorageUtils.formatSize(sizeBytes)
            return@withContext "📁 Downloads mappa mérete: $sizeFormatted"
        } catch (e: Exception) {
            return@withContext "Hiba a mappa méretének számolásakor: ${e.message}"
        }
    }

    private fun calculateSize(directory: File): Long {
        var totalLength = 0L
        val files = directory.listFiles() ?: return 0L
        for (file in files) {
            totalLength += if (file.isFile) {
                file.length()
            } else {
                calculateSize(file)
            }
        }
        return totalLength
    }
}
