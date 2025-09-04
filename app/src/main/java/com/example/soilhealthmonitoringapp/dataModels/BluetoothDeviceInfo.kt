package com.example.soilhealthmonitoringapp.dataModels

data class BluetoothDeviceInfo(
    val name: String,
    val address: String,
    val rssi: Int,
    val isConnected: Boolean = false
)
