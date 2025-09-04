package com.example.soilhealthmonitoringapp.AppScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soilhealthmonitoringapp.AppComponents.ReadingHistoryItem
import com.example.soilhealthmonitoringapp.AppComponents.StatisticItem
import com.example.soilhealthmonitoringapp.AppComponents.generateSampleReadings
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit
) {
    // Sample data - in real app, this would come from Firebase
    val readings = remember {
        generateSampleReadings()
    }

    var selectedFilter by remember { mutableStateOf("All") }
    val filterOptions = listOf("All", "Last 7 Days", "Last 30 Days")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Reading History") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Export history */ }) {
                    Icon(Icons.Default.FileDownload, contentDescription = "Export")
                }
            }
        )

        // Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            items(filterOptions) { filter ->
                FilterChip(
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    selected = selectedFilter == filter
                )
            }
        }

        // Statistics Summary
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatisticItem(
                    label = "Avg Temp",
                    value = "${readings.map { it.temperature }.average().toInt()}°C"
                )
                StatisticItem(
                    label = "Avg Moisture",
                    value = "${readings.map { it.moisture }.average().toInt()}%"
                )
                StatisticItem(
                    label = "Total Readings",
                    value = readings.size.toString()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Readings List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(readings) { reading ->
                ReadingHistoryItem(reading = reading)
            }
        }
    }
}
