package com.example.allinonetester.domain.usercases

import android.content.Context
import com.example.allinonetester.utils.DeviceUtils

class GetMobileNetworkTypeUseCase(private val context: Context) {
    suspend operator fun invoke(): String {
        return "Mobil hálózat típusa: ${DeviceUtils.getMobileNetworkType(context)} 📶"
    }
}
