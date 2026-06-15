package com.example.allinonetester.domain.usercases

import android.content.Context
import com.example.allinonetester.utils.DeviceUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetRamInfoUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): String {
        return DeviceUtils.getRamInfo(context)
    }
}
