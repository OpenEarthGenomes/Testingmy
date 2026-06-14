package com.example.allinonetester.domain.usercases

import android.content.Context
import com.example.allinonetester.utils.DeviceUtils

class GetNetworkStateUseCase(private val context: Context) {
    suspend operator fun invoke(): String {
        return DeviceUtils.getNetworkState(context)
    }
}
