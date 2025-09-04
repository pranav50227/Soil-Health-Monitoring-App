package com.example.soilhealthmonitoringapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.soilhealthmonitoringapp.ui.theme.SoilHealthMonitoringAppTheme
import android.Manifest
import android.util.Log


class MainActivity : ComponentActivity() {
        private val bluetoothManager by lazy { getSystemService(BluetoothManager::class.java) }
        private val bluetoothAdapter by lazy { bluetoothManager?.adapter }

        private val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            // Handle permission results
        }

        private val enableBluetoothLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // Handle Bluetooth enable result
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            Log.d("SoilApp", "MainActivity onCreate called");
            enableEdgeToEdge()
            checkAndRequestPermissions()
            setContent {
                Log.d("SoilApp", "Layout set successfully");
                SoilHealthMonitoringAppTheme {
                    Log.d("SoilApp", "Theme launched");
                    val navController = rememberNavController()
                    HealthApp(
                        navController = navController,
                        bluetoothAdapter = bluetoothAdapter,
                        onEnableBluetooth = { enableBluetooth() }
                    )
                }

            }
        }

        private fun checkAndRequestPermissions() {
            val requiredPermissions = mutableListOf<String>()

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                requiredPermissions.add(Manifest.permission.BLUETOOTH_SCAN)
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                requiredPermissions.add(Manifest.permission.BLUETOOTH_CONNECT)
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requiredPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            if (requiredPermissions.isNotEmpty()) {
                requestPermissionLauncher.launch(requiredPermissions.toTypedArray())
            }
        }

        private fun enableBluetooth() {
            if (bluetoothAdapter?.isEnabled == false) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                enableBluetoothLauncher.launch(enableBtIntent)
            }
        }
}