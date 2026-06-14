package com.example.allinonetester.domain.usercases

import android.content.Context
import com.example.allinonetester.utils.DeviceUtils

class ListSensorsUseCase(private val context: Context) {
    suspend operator fun invoke(): String {
        val sensors = DeviceUtils.getAvailableSensors(context)
        return "Elérhető szenzorok:\n${sensors.take(10).joinToString("\n")}${if (sensors.size > 10) "\n... és ${sensors.size - 10} további" else ""}"
    }
}
