package com.example.soilhealthmonitoringapp

import android.app.Application

class SoilHealthApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize your app-wide components here
        // Example: Firebase, Crash reporting, Analytics, etc.

        // For now, just basic setup
        println("SoilHealthApplication started")

        // TODO: Add Firebase initialization when needed
        // FirebaseApp.initializeApp(this)

        // TODO: Add other SDK initializations
    }
}