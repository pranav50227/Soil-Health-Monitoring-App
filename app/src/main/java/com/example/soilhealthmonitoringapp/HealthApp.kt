package com.example.soilhealthmonitoringapp

import android.app.Application
import android.bluetooth.BluetoothAdapter
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.soilhealthmonitoringapp.AppScreens.DeviceSearchScreen
import com.example.soilhealthmonitoringapp.AppScreens.HistoryScreen
import com.example.soilhealthmonitoringapp.AppScreens.HomeScreen
import com.example.soilhealthmonitoringapp.AppScreens.LoginScreen
import com.example.soilhealthmonitoringapp.AppScreens.ReportsScreen
import com.example.soilhealthmonitoringapp.AppScreens.SignUpScreen
import com.example.soilhealthmonitoringapp.navigation.NavRoutes
import com.example.soilhealthmonitoringapp.navigation.NavRoutes.DeviceSearchScreen
import com.example.soilhealthmonitoringapp.navigation.NavRoutes.HistoryScreen
import com.example.soilhealthmonitoringapp.navigation.NavRoutes.HomeScreen
import com.example.soilhealthmonitoringapp.navigation.NavRoutes.LoginScreen
import com.example.soilhealthmonitoringapp.navigation.NavRoutes.ReportsScreen

@Composable
fun HealthApp (
    navController: NavHostController,
    bluetoothAdapter: BluetoothAdapter?,
    onEnableBluetooth: () -> Unit
){

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") },
                onNavigateToSignup = { navController.navigate("signup") }
            )
        }

        composable("signup") {
            SignUpScreen(
                onSignupSuccess = { navController.navigate("home") },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable("home") {
            HomeScreen(
                onNavigateToReports = { navController.navigate("reports") },
                onNavigateToHistory = { navController.navigate("history") },
                onNavigateToDeviceSearch = { navController.navigate("device_search") },
                bluetoothAdapter = bluetoothAdapter,
                onEnableBluetooth = onEnableBluetooth
            )
        }

        composable("reports") {
            ReportsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("history") {
            HistoryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("device_search") {
            DeviceSearchScreen(
                onNavigateBack = { navController.popBackStack() },
                bluetoothAdapter = bluetoothAdapter,
                onDeviceSelected = { device ->
                    // Handle device selection and navigate back to home
                    navController.popBackStack()
                }
            )
        }
    }
}