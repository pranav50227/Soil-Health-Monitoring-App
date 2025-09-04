package com.example.soilhealthmonitoringapp.AppScreens

import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soilhealthmonitoringapp.AppComponents.DeviceListItem
import com.example.soilhealthmonitoringapp.dataModels.BluetoothDeviceInfo
import com.example.soilhealthmonitoringapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceSearchScreen(
    onNavigateBack: () -> Unit,
    bluetoothAdapter: BluetoothAdapter?,
    onDeviceSelected: (BluetoothDeviceInfo) -> Unit
) {
    var isScanning by remember { mutableStateOf(false) }
    var discoveredDevices by remember { mutableStateOf(listOf<BluetoothDeviceInfo>()) }
    var connectedDevice by remember { mutableStateOf<BluetoothDeviceInfo?>(null) }

    // Sample discovered devices for demonstration
    LaunchedEffect(isScanning) {
        if (isScanning) {
            kotlinx.coroutines.delay(2000)
            discoveredDevices = listOf(
                BluetoothDeviceInfo("SoilSensor-001", "AA:BB:CC:DD:EE:01", -45),
                BluetoothDeviceInfo("SoilSensor-002", "AA:BB:CC:DD:EE:02", -67),
                BluetoothDeviceInfo("AgriMonitor-Pro", "AA:BB:CC:DD:EE:03", -52),
                BluetoothDeviceInfo("EcoSensor-X1", "AA:BB:CC:DD:EE:04", -78)
            )
            isScanning = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Find Soil Sensors") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        // Bluetooth Status
        if (bluetoothAdapter?.isEnabled != true) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(id = R.drawable.close_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Bluetooth is disabled. Please enable it to scan for devices.",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }

        // Connected Device Card
        connectedDevice?.let { device ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.bluetooth_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Connected to",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = device.name,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    TextButton(
                        onClick = { connectedDevice = null }
                    ) {
                        Text("Disconnect")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Scan Button
        Button(
            onClick = {
                if (bluetoothAdapter?.isEnabled == true) {
                    isScanning = true
                    discoveredDevices = emptyList()
                    // TODO: Start actual Bluetooth scanning
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = bluetoothAdapter?.isEnabled == true && !isScanning
        ) {
            if (isScanning) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Scanning...")
            } else {
                Icon(Icons.Default.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Scan for Devices")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Device List Header
        if (discoveredDevices.isNotEmpty()) {
            Text(
                text = "Discovered Devices",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // Discovered Devices List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(discoveredDevices) { device ->
                DeviceListItem(
                    device = device,
                    onConnect = {
                        connectedDevice = device
                        onDeviceSelected(device)
                    }
                )
            }
        }

        if (discoveredDevices.isEmpty() && !isScanning) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painterResource(R.drawable.bluetooth_searching_24px),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No devices found",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Make sure your soil sensor is powered on and in pairing mode",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}