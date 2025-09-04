package com.example.soilhealthmonitoringapp.AppScreens

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soilhealthmonitoringapp.AppComponents.SoilMetricCard
import com.example.soilhealthmonitoringapp.AppComponents.getMoistureRecommendation
import com.example.soilhealthmonitoringapp.AppComponents.getMoistureStatus
import com.example.soilhealthmonitoringapp.AppComponents.getOverallRecommendation
import com.example.soilhealthmonitoringapp.AppComponents.getOverallStatus
import com.example.soilhealthmonitoringapp.AppComponents.getTemperatureRecommendation
import com.example.soilhealthmonitoringapp.AppComponents.getTemperatureStatus
import com.example.soilhealthmonitoringapp.R
import com.example.soilhealthmonitoringapp.dataModels.SoilReading
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    onNavigateBack: () -> Unit
) {
    // Simulate latest reading data
    val latestReading = remember {
        SoilReading(
            temperature = 24.5f,
            moisture = 65.2f,
            timestamp = System.currentTimeMillis()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Latest Report") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Reading Time
        Text(
            text = "Last Updated: ${SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(
                Date(latestReading.timestamp)
            )}",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Temperature Card
        SoilMetricCard(
            title = "Temperature",
            value = "${latestReading.temperature}°C",
            painterResource(id = R.drawable.device_thermostat_24px),
            status = getTemperatureStatus(latestReading.temperature),
            recommendation = getTemperatureRecommendation(latestReading.temperature)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Moisture Card
        SoilMetricCard(
            title = "Moisture",
            value = "${latestReading.moisture}%",
            painterResource(id = R.drawable.water_24px),
            status = getMoistureStatus(latestReading.moisture),
            recommendation = getMoistureRecommendation(latestReading.moisture)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Overall Health Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(id = R.drawable.eco_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Overall Soil Health",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                val overallStatus = getOverallStatus(latestReading.temperature, latestReading.moisture)
                Text(
                    text = overallStatus,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )

                Text(
                    text = getOverallRecommendation(latestReading.temperature, latestReading.moisture),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    // TODO: Export report functionality
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Export")
            }

            Button(
                onClick = {
                    // TODO: Save to Firebase
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.Done, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save")
            }
        }
    }
}