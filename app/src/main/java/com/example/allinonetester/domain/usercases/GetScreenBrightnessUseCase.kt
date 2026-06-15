package com.example.allinonetester.domain.usercases

import android.content.Context
import com.example.allinonetester.utils.DeviceUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetScreenBrightnessUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(): String {
        val brightness = DeviceUtils.getScreenBrightness(context)
        return "Képernyő fényerő: $brightness / 255 ☀️"
    }
}
