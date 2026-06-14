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

    /**
     * Visszaadja az eszköz belső tárhelyének szabad kapacitását bájtokban.
     */
    fun getFreeInternalSpace(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return availableBlocks * blockSize
    }

    /**
     * Visszaadja az eszköz belső tárhelyének teljes kapacitását bájtokban.
     */
    fun getTotalInternalSpace(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        return totalBlocks * blockSize
    }

    /**
     * Visszaadja az alkalmazás belső gyorsítótárának (cache) mappáját.
     */
    fun getAppCacheDirectory(): File {
        return context.cacheDir
    }

    /**
     * Visszaadja az alkalmazás külső gyorsítótárának mappáját (ha elérhető).
     */
    fun getAppExternalCacheDirectory(): File? {
        return context.externalCacheDir
    }

    /**
     * Törli a megadott mappát és annak minden tartalmát rekurzívan.
     * @return Igaz, ha a törlés sikeres volt, különben hamis.
     */
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
