package com.example.allinonetester.domain.usercases

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

class FileOperationsUseCase @Inject constructor(
    private val context: Context
) {
    fun deleteInternalFile(filePath: String): Boolean {
        val file = File(filePath)
        return if (file.exists()) file.delete() else false
    }

    fun copyInternalFile(sourcePath: String, destPath: String): Boolean {
        val source = File(sourcePath)
        val dest = File(destPath)
        if (!source.exists()) return false
        
        return try {
            FileInputStream(source).use { input ->
                FileOutputStream(dest).use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun deleteExternalFile(treeUriStr: String, relativePath: String): Boolean {
        return try {
            val treeUri = Uri.parse(treeUriStr)
            val rootFolder = DocumentFile.fromTreeUri(context, treeUri) ?: return false
            val fileToDelete = findDocumentFile(rootFolder, relativePath)
            fileToDelete?.delete() ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun copyToExternal(sourcePath: String, treeUriStr: String, targetFileName: String): Boolean {
        val sourceFile = File(sourcePath)
        if (!sourceFile.exists()) return false

        return try {
            val treeUri = Uri.parse(treeUriStr)
            val rootFolder = DocumentFile.fromTreeUri(context, treeUri) ?: return false
            
            val existingFile = rootFolder.findFile(targetFileName)
            existingFile?.delete()

            val newFile = rootFolder.createFile("*/*", targetFileName) ?: return false
            
            context.contentResolver.openOutputStream(newFile.uri).use { outputStream ->
                FileInputStream(sourceFile).use { inputStream ->
                    if (outputStream != null) {
                        inputStream.copyTo(outputStream)
                    }
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun findDocumentFile(root: DocumentFile, relativePath: String): DocumentFile? {
        val parts = relativePath.split("/")
        var current: DocumentFile? = root
        for (part in parts) {
            if (part.isEmpty()) continue
            current = current?.findFile(part)
            if (current == null) return null
        }
        return current
    }
}

