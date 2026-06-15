package com.example.allinonetester.domain.usercases

import android.content.Context
import android.os.Environment
import android.os.StatFs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetStorageInfoUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): String {
        return try {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong
            val availableBlocks = stat.availableBlocksLong

            val totalBytes = totalBlocks * blockSize
            val availableBytes = availableBlocks * blockSize
            val usedBytes = totalBytes - availableBytes

            val totalGB = totalBytes / (1024 * 1024 * 1024).toDouble()
            val availableGB = availableBytes / (1024 * 1024 * 1024).toDouble()
            val usedGB = usedBytes / (1024 * 1024 * 1024).toDouble()

            String.format("Összesen: %.2f GB | Szabad: %.2f GB | Használt: %.2f GB", totalGB, availableGB, usedGB)
        } catch (e: Exception) {
            "Tárhely információ nem elérhető"
        }
    }
}
