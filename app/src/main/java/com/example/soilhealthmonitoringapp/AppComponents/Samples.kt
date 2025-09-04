package com.example.soilhealthmonitoringapp.AppComponents

import com.example.soilhealthmonitoringapp.dataModels.SoilReading
import java.util.Calendar

fun generateSampleReadings(): List<SoilReading> {
    val readings = mutableListOf<SoilReading>()
    val calendar = Calendar.getInstance()

    for (i in 0..29) {
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        readings.add(
            SoilReading(
                temperature = (18..32).random() + (0..9).random() / 10f,
                moisture = (35..75).random() + (0..9).random() / 10f,
                timestamp = calendar.timeInMillis
            )
        )
    }

    return readings.reversed()
}