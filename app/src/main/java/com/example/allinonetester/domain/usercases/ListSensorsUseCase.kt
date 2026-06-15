package com.example.allinonetester.domain.usercases

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ListSensorsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): String {
        return try {
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
            
            if (deviceSensors.isEmpty()) {
                "Nem találhatók szenzorok a készüléken."
            } else {
                deviceSensors.joinToString("\n") { sensor ->
                    "${sensor.name} (Gyártó: ${sensor.vendor}, Típus: ${sensor.type})"
                }
            }
        } catch (e: Exception) {
            "Hiba a szenzorok lekérdezésekor"
        }
    }
}
