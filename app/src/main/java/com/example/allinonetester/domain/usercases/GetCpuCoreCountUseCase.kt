package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.DeviceUtils
import javax.inject.Inject

class GetCpuCoreCountUseCase @Inject constructor() {
    suspend operator fun invoke(): String {
        return "CPU magok száma: ${DeviceUtils.getCpuCoreCount()} 🧠"
    }
}
