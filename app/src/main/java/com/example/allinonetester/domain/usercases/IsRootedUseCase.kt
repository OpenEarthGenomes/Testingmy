package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.DeviceUtils
import javax.inject.Inject

class IsRootedUseCase @Inject constructor() {
    suspend operator fun invoke(): String {
        return "Root hozzáférés: ${if (DeviceUtils.isRooted()) "IGEN ⚠️" else "NEM ✅"}"
    }
}
