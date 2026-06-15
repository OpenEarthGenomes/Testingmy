package com.example.allinonetester.domain.usercases

import android.os.Build
import javax.inject.Inject

class GetDeviceInfoUseCase @Inject constructor() {
    suspend operator fun invoke(): String {
        return """
            📱 Eszköz modell: ${Build.MANUFACTURER} ${Build.MODEL}
            🤖 Android verzió: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})
        """.trimIndent()
    }
}
