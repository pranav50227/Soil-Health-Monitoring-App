package com.example.soilhealthmonitoringapp.AppScreens

import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.example.soilhealthmonitoringapp.AppComponents.QuickStatItem
import com.example.soilhealthmonitoringapp.R
import com.example.soilhealthmonitoringapp.dataModels.SoilReading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToReports: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToDeviceSearch: () -> Unit,
    bluetoothAdapter: BluetoothAdapter?,
    onEnableBluetooth: () -> Unit
) {
    var isConnected by remember { mutableStateOf(false) }
    var connectedDeviceName by remember { mutableStateOf("") }
    var lastReading by remember { mutableStateOf<SoilReading?>(null) }
    var isTestingInProgress by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Soil Health Monitor") },
            actions = {
                IconButton(onClick = onNavigateToDeviceSearch) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Find Devices",
                        tint = if (isConnected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { /* Settings */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        )

        // Connection Status Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isConnected)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isConnected) Icons.Default.CheckCircle else Icons.Default.Error,
                    contentDescription = null,
                    tint = if (isConnected)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isConnected) "Connected" else "Not Connected",
                        fontWeight = FontWeight.Medium,
                        color = if (isConnected)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onErrorContainer
                    )
                    if (isConnected && connectedDeviceName.isNotBlank()) {
                        Text(
                            text = connectedDeviceName,
                            fontSize = 12.sp,
                            color = if (isConnected)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else
                                MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Main Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Test Button
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Button(
                    onClick = {
                        if (bluetoothAdapter?.isEnabled == false) {
                            onEnableBluetooth()
                        } else if (isConnected) {
                            isTestingInProgress = true
                            // TODO: Implement Bluetooth command to fetch new reading
                            // Simulate test completion after 3 seconds
                            // In real implementation, this would trigger when Bluetooth data is received
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    enabled = !isTestingInProgress,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isTestingInProgress) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Testing...", fontSize = 14.sp)
                        } else {
                            Icon(
                                painterResource(id = R.drawable.science_24px),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Test", fontWeight = FontWeight.Bold)
                            Text("Fetch New Reading", fontSize = 12.sp)
                        }
                    }
                }
            }

            // Reports Button
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Button(
                    onClick = onNavigateToReports,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painterResource(id = R.drawable.history_24px),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Reports", fontWeight = FontWeight.Bold)
                        Text("Latest Reading", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Stats Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Last Reading",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (lastReading != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        QuickStatItem(
                            painterResource(id = R.drawable.device_thermostat_24px),
                            label = "Temperature",
                            value = "${lastReading!!.temperature}°C"
                        )
                        QuickStatItem(
                            painterResource(id = R.drawable.eco_24px),
                            label = "Moisture",
                            value = "${lastReading!!.moisture}%"
                        )
                    }
                } else {
                    Text(
                        text = "No readings available. Press 'Test' to fetch data.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // History Button
        OutlinedButton(
            onClick = onNavigateToHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Warning, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("View History")
        }
    }

    // Simulate test completion
    LaunchedEffect(isTestingInProgress) {
        if (isTestingInProgress) {
            kotlinx.coroutines.delay(3000)
            lastReading = SoilReading(
                temperature = (20..35).random().toFloat(),
                moisture = (30..80).random().toFloat(),
                timestamp = System.currentTimeMillis()
            )
            isTestingInProgress = false
        }
    }
}
