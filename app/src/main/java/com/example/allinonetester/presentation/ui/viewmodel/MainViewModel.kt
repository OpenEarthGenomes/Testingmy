package com.example.allinonetester.presentation.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinonetester.domain.usercases.ClearRamUseCase
import com.example.allinonetester.domain.usercases.FileOperationsUseCase
import com.example.allinonetester.domain.usercases.GetFolderSizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFolderSizeUseCase: GetFolderSizeUseCase,
    private val clearRamUseCase: ClearRamUseCase,
    private val fileOperationsUseCase: FileOperationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<String>("Készen áll")
    val uiState: StateFlow<String> = _uiState

    var sdCardUri = mutableStateOf<String?>(null)

    fun checkFolderSize(path: String) {
        viewModelScope.launch {
            _uiState.value = "Méret számítása..."
            val size = getFolderSizeUseCase(path)
            _uiState.value = "Mappa mérete: $size"
        }
    }

    fun runRamCleanup() {
        viewModelScope.launch {
            _uiState.value = "RAM tisztítása folyamatban..."
            val freedMb = clearRamUseCase()
            _uiState.value = "Sikeres takarítás! Felszabadítva: $freedMb MB RAM"
        }
    }

    fun deleteFile(path: String, isExternal: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = "Törlés..."
            val success = if (isExternal) {
                val uriStr = sdCardUri.value
                if (uriStr != null) {
                    fileOperationsUseCase.deleteExternalFile(uriStr, path)
                } else {
                    _uiState.value = "Hiba: SD kártya nincs engedélyezve!"
                    return@launch
                }
            } else {
                fileOperationsUseCase.deleteInternalFile(path)
            }
            _uiState.value = if (success) "Fájl sikeresen törölve!" else "Törlés sikertelen."
        }
    }

    fun copyFile(source: String, dest: String, toExternal: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = "Másolás..."
            val success = if (toExternal) {
                val uriStr = sdCardUri.value
                if (uriStr != null) {
                    fileOperationsUseCase.copyToExternal(source, uriStr, dest)
                } else {
                    _uiState.value = "Hiba: SD kártya nincs engedélyezve!"
                    return@launch
                }
            } else {
                fileOperationsUseCase.copyInternalFile(source, dest)
            }
            _uiState.value = if (success) "Sikeres másolás!" else "Másolás sikertelen."
        }
    }
}
