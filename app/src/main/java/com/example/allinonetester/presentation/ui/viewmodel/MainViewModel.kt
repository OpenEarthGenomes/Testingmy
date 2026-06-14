package com.example.allinonetester.presentation.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinonetester.domain.usercases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class TestButton(val id: String, val title: String)

data class MainUiState(
    val results: List<String> = emptyList(),
    val testButtons: List<TestButton> = emptyList(),
    val loadingTestId: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val context: Context,
    private val checkInternetUseCase: CheckInternetUseCase,
    private val dnsLookupUseCase: DnsLookupUseCase,
    private val getPublicIpUseCase: GetPublicIpUseCase,
    private val getResponseTimeUseCase: GetResponseTimeUseCase,
    private val speedTestUseCase: SpeedTestUseCase,
    private val pingUseCase: PingUseCase,
    private val portScanUseCase: PortScanUseCase,
    private val getFolderSizeUseCase: GetFolderSizeUseCase,
    private val getBatteryStatusUseCase: GetBatteryStatusUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getRamInfoUseCase: GetRamInfoUseCase,
    private val getStorageInfoUseCase: GetStorageInfoUseCase,
    private val getDeviceInfoUseCase: GetDeviceInfoUseCase,
    private val getScreenBrightnessUseCase: GetScreenBrightnessUseCase,
    private val getCpuCoreCountUseCase: GetCpuCoreCountUseCase,
    private val listInstalledAppsUseCase: ListInstalledAppsUseCase,
    private val getMobileNetworkTypeUseCase: GetMobileNetworkTypeUseCase,
    private val listSensorsUseCase: ListSensorsUseCase,
    private val getDisplayInfoUseCase: GetDisplayInfoUseCase,
    private val getSecurityPatchUseCase: GetSecurityPatchUseCase,
    private val isRootedUseCase: IsRootedUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    init {
        uiState = uiState.copy(
            testButtons = listOf(
                TestButton("internet", "🌐 Internet elérés ellenőrzés"),
                TestButton("dns", "🔍 DNS lookup (clcoding.com)"),
                TestButton("public_ip", "🌍 Publikus IP cím"),
                TestButton("response_time", "⏱️ Weboldal válaszidő"),
                TestButton("speed", "⚡ Sebességteszt"),
                TestButton("ping", "📡 Ping (1.1.1.1)"),
                TestButton("port_scan", "🔓 Port scanner (1.1.1.1)"),
                TestButton("folder_size", "📁 Letöltések mappa mérete"),
                TestButton("battery", "🔋 Akku állapot"),
                TestButton("network_state", "📶 Hálózati állapot"),
                TestButton("ram", "💾 RAM használat"),
                TestButton("storage", "💿 Tárhely információ"),
                TestButton("device", "📱 Eszköz info"),
                TestButton("brightness", "☀️ Képernyő fényerő"),
                TestButton("cpu", "🧠 CPU magok"),
                TestButton("apps", "📦 Telepített alkalmazások"),
                TestButton("mobile_network", "📱 Mobil hálózat típusa"),
                TestButton("sensors", "🎯 Szenzorok"),
                TestButton("display", "🖥️ Kijelző felbontás"),
                TestButton("security_patch", "🔒 Biztonsági patch"),
                TestButton("root", "⚠️ Root ellenőrzés")
            )
        )
    }

    fun runTest(testId: String) {
        viewModelScope.launch {
            uiState = uiState.copy(loadingTestId = testId)
            val result = when (testId) {
                "internet" -> checkInternetUseCase()
                "dns" -> dnsLookupUseCase()
                "public_ip" -> getPublicIpUseCase()
                "response_time" -> getResponseTimeUseCase()
                "speed" -> speedTestUseCase()
                "ping" -> pingUseCase()
                "port_scan" -> portScanUseCase()
                "folder_size" -> getFolderSizeUseCase(android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS).absolutePath)
                "battery" -> getBatteryStatusUseCase()
                "network_state" -> getNetworkStateUseCase()
                "ram" -> getRamInfoUseCase()
                "storage" -> getStorageInfoUseCase()
                "device" -> getDeviceInfoUseCase()
                "brightness" -> getScreenBrightnessUseCase()
                "cpu" -> getCpuCoreCountUseCase()
                "apps" -> listInstalledAppsUseCase(0)
                "mobile_network" -> getMobileNetworkTypeUseCase()
                "sensors" -> listSensorsUseCase()
                "display" -> getDisplayInfoUseCase()
                "security_patch" -> getSecurityPatchUseCase()
                "root" -> isRootedUseCase()
                else -> "Ismeretlen teszt"
            }
            addResult("✅ ${uiState.testButtons.find { it.id == testId }?.title ?: testId}\n$result")
            uiState = uiState.copy(loadingTestId = null)
        }
    }

    fun runAllTests() {
        viewModelScope.launch {
            uiState.testButtons.forEach { button ->
                runTest(button.id)
                kotlinx.coroutines.delay(500)
            }
        }
    }

    fun saveResults() {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "tester_results_$timestamp.txt"
        val file = java.io.File(context.getExternalFilesDir(null), fileName)
        file.writeText(uiState.results.joinToString("\n\n" + "=".repeat(50) + "\n\n"))
        addResult("💾 Eredmények mentve: ${file.absolutePath}")
    }

    fun clearResults() {
        uiState = uiState.copy(results = emptyList())
    }

    private fun addResult(result: String) {
        uiState = uiState.copy(
            results = uiState.results + result
        )
    }
}
