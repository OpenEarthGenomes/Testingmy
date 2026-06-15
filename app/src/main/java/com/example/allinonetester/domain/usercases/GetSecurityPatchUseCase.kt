package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.DeviceUtils
import javax.inject.Inject

class GetSecurityPatchUseCase @Inject constructor() {
    suspend operator fun invoke(): String {
        return DeviceUtils.getSecurityPatch()
    }
}
