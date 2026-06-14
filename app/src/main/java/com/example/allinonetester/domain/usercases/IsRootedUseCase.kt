package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.DeviceUtils

class IsRootedUseCase {
    suspend operator fun invoke(): String {
        return "Root hozzáférés: ${if (DeviceUtils.isRooted()) "IGEN ⚠️" else "NEM ✅"}"
    }
}
