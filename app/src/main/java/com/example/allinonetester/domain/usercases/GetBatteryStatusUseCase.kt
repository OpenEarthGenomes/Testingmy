package com.example.allinonetester.domain.usercases

import android.content.Context
import com.example.allinonetester.utils.DeviceUtils

class GetBatteryStatusUseCase(private val context: Context) {
    suspend operator fun invoke(): String {
        return DeviceUtils.getBatteryInfo(context)
    }
}
