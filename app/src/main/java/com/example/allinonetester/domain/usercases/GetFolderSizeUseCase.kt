package com.example.allinonetester.domain.usercases

import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFolderSizeUseCase @Inject constructor() {

    /**
     * Kiszámítja egy adott mappa teljes méretét bájtokban, a háttérszálon futva.
     * @param folderPath A célmappa abszolút elérési útja.
     * @return A mappa mérete bájtokban (Long). Ha nem létezik vagy nem mappa, 0L.
     */
    suspend operator fun invoke(folderPath: String): Long = withContext(Dispatchers.IO) {
        val folder = File(folderPath)
        if (!folder.exists() || !folder.isDirectory) {
            return@withContext 0L
        }
        return@withContext calculateSize(folder)
    }

    private fun calculateSize(directory: File): Long {
        var totalLength: Long = 0
        val files = directory.listFiles() ?: return 0L

        for (file in files) {
            totalLength += if (file.isFile) {
                file.length()
            } else {
                calculateSize(file) // Rekurzív hívás az almappákhoz
            }
        }
        return totalLength
    }
}
