package com.example.allinonetester.domain.usercases

import com.example.allinonetester.utils.DeviceUtils

class GetStorageInfoUseCase {
    suspend operator fun invoke(): String {
        return DeviceUtils.getStorageInfo()
    }
}
