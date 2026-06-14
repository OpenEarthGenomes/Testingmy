package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.DeviceUtils

class GetSecurityPatchUseCase {
    suspend operator fun invoke(): String {
        return DeviceUtils.getSecurityPatch()
    }
}
