package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.DeviceUtils

class GetRamInfoUseCase {
    suspend operator fun invoke(): String {
        return DeviceUtils.getRamInfo()
    }
}
