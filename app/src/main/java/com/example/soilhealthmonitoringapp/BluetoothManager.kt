package com.example.soilhealthmonitoringapp

import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.soilhealthmonitoringapp.dataModels.BluetoothDeviceInfo
import com.example.soilhealthmonitoringapp.dataModels.SoilReading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class BluetoothManager(private val context: Context) {
    @SuppressLint("ServiceCast")
    private val bluetoothManager : BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    @SuppressLint("ServiceCast")
    private val bluetoothAdapter : BluetoothAdapter? = bluetoothManager.adapter

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    private val _discoveredDevices = MutableStateFlow<List<BluetoothDeviceInfo>>(emptyList())
    val discoveredDevices: StateFlow<List<BluetoothDeviceInfo>> = _discoveredDevices

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState

    private val _soilData = MutableStateFlow<SoilReading?>(null)
    val soilData: StateFlow<SoilReading?> = _soilData

    private var bluetoothGatt: BluetoothGatt? = null

    // UUID for soil sensor service and characteristics
    companion object {
        private val SOIL_SERVICE_UUID = java.util.UUID.fromString("12345678-1234-1234-1234-123456789abc")
        private val SOIL_DATA_CHARACTERISTIC_UUID = java.util.UUID.fromString("87654321-4321-4321-4321-cba987654321")
    }

    sealed class ConnectionState {
        object Disconnected : ConnectionState()
        object Connecting : ConnectionState()
        object Connected : ConnectionState()
        data class Error(val message: String) : ConnectionState()
    }

    @SuppressLint("MissingPermission")
    fun startScanning() {
        if (!hasBluetoothPermissions()) return
        if (bluetoothAdapter?.isEnabled != true) return

        _isScanning.value = true
        _discoveredDevices.value = emptyList()

        val scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: android.bluetooth.le.ScanResult?) {
                result?.let { scanResult ->
                    val device = scanResult.device
                    val deviceInfo = BluetoothDeviceInfo(
                        name = device.name ?: "Unknown Device",
                        address = device.address,
                        rssi = scanResult.rssi
                    )

                    val currentDevices = _discoveredDevices.value.toMutableList()
                    if (!currentDevices.any { it.address == deviceInfo.address }) {
                        currentDevices.add(deviceInfo)
                        _discoveredDevices.value = currentDevices
                    }
                }
            }

            override fun onScanFailed(errorCode: Int) {
                _isScanning.value = false
                _connectionState.value = ConnectionState.Error("Scan failed with error: $errorCode")
            }
        }

        val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
        bluetoothLeScanner?.startScan(scanCallback)

        // Stop scanning after 10 seconds
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
            kotlinx.coroutines.delay(10000)
            bluetoothLeScanner?.stopScan(scanCallback)
            _isScanning.value = false
        }
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(deviceInfo: BluetoothDeviceInfo) {
        if (!hasBluetoothPermissions()) return

        _connectionState.value = ConnectionState.Connecting

        val device = bluetoothAdapter?.getRemoteDevice(deviceInfo.address)

        val gattCallback = object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        _connectionState.value = ConnectionState.Connected
                        gatt?.discoverServices()
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        _connectionState.value = ConnectionState.Disconnected
                        bluetoothGatt = null
                    }
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    val service = gatt?.getService(SOIL_SERVICE_UUID)
                    val characteristic = service?.getCharacteristic(SOIL_DATA_CHARACTERISTIC_UUID)
                    characteristic?.let {
                        gatt.setCharacteristicNotification(it, true)
                    }
                }
            }

            override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
                characteristic?.let {
                    val data = String(it.value)
                    parseAndUpdateSoilData(data)
                }
            }
        }

        bluetoothGatt = device?.connectGatt(context, false, gattCallback)
    }

    @SuppressLint("MissingPermission")
    fun requestNewReading() {
        if (!hasBluetoothPermissions()) return

        bluetoothGatt?.let { gatt ->
            val service = gatt.getService(SOIL_SERVICE_UUID)
            val characteristic = service?.getCharacteristic(SOIL_DATA_CHARACTERISTIC_UUID)
            characteristic?.let {
                // Send command to request new reading
                val command = "GET_READING".toByteArray()
                it.value = command
                gatt.writeCharacteristic(it)
            }
        }
    }

    private fun parseAndUpdateSoilData(jsonData: String) {
        try {
            val json = JSONObject(jsonData)
            val reading = SoilReading(
                temperature = json.getDouble("temperature").toFloat(),
                moisture = json.getDouble("moisture").toFloat(),
                timestamp = System.currentTimeMillis(),
                deviceId = json.optString("deviceId", "")
            )
            _soilData.value = reading
        } catch (e: Exception) {
            _connectionState.value = ConnectionState.Error("Failed to parse soil data: ${e.message}")
        }
    }

    private fun hasBluetoothPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.BLUETOOTH_SCAN
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
        _connectionState.value = ConnectionState.Disconnected
    }
}