package com.example.allinonetester.domain.usercases

import android.os.Build

class GetDeviceInfoUseCase {
    suspend operator fun invoke(): String {
        return """
            📱 Eszköz modell: ${Build.MANUFACTURER} ${Build.MODEL}
            🤖 Android verzió: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})
        """.trimIndent()
    }
}
