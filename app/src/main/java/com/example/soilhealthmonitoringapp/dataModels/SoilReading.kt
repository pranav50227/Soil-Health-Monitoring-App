package com.example.soilhealthmonitoringapp.dataModels

data class SoilReading(
    val temperature: Float,
    val moisture: Float,
    val timestamp: Long,
    val deviceId: String = ""
)
