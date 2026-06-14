package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.DeviceUtils

class GetCpuCoreCountUseCase {
    suspend operator fun invoke(): String {
        return "CPU magok száma: ${DeviceUtils.getCpuCoreCount()} 🧠"
    }
}
