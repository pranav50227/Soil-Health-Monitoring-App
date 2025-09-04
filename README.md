Soil Health Monitoring App

A comprehensive Android application for monitoring soil health parameters using Bluetooth-enabled sensors. Built with Jetpack Compose, MVVM architecture, and modern Android development practices.

📱 Features

Real-time Soil Monitoring: pH, moisture, temperature, and nutrient levels

Bluetooth Connectivity: BLE & Classic support for soil sensors

Modern UI: Jetpack Compose-based smooth experience

Navigation: Multi-screen navigation with bottom nav bar

Permissions: Automatic handling of Bluetooth & Location permissions

Data Visualization: Real-time charts and graphs for sensor data

📸 Screenshots

Add your app screenshots here

⚙️ Requirements
System

Android Version: API 21 (Lollipop) and above

Target SDK: API 35 (Android 15)

Bluetooth: Bluetooth 4.0+ (BLE recommended)

Permissions: Location + Bluetooth

Hardware

Android device with Bluetooth capability

Compatible soil monitoring sensors (see Supported Sensors
)

🚀 Installation
Option 1: Install APK

Download the latest APK from Releases

Enable Install from Unknown Sources in device settings

Install the APK

Option 2: Build from Source
git clone https://github.com/your-repo/soil-health-monitoring-app.git
cd soil-health-monitoring-app


Open in Android Studio

Sync project & resolve dependencies

Run on emulator/device

./gradlew assembleDebug

📡 Bluetooth Setup
Prerequisites

Enable Bluetooth on device

Grant permissions when prompted

Required Permissions

Android 12+ (API 31+)

BLUETOOTH_SCAN

BLUETOOTH_CONNECT

ACCESS_FINE_LOCATION

Android 11 and below

BLUETOOTH

BLUETOOTH_ADMIN

ACCESS_FINE_LOCATION

Pairing Process

Enable Bluetooth (app will prompt if disabled)

Grant required permissions

Go to Devices → Tap Scan

Select sensor from list

Pair (PIN may be required)

Start real-time monitoring

🛠 Troubleshooting

Device not found: Ensure sensor in pairing mode, within 10m, restart Bluetooth

Connection fails: Clear Bluetooth cache, unpair/re-pair, restart app & sensor

Disconnections: Check sensor battery, reduce interference, keep within range

Permission issues: Grant permissions manually (Settings → Apps → Soil App → Permissions)

🌱 Compatible Sensors

Generic BLE Soil Sensors (pH & moisture)

Arduino-based Sensors with Bluetooth modules

ESP32/ESP8266 DIY sensors

Requirements:

Bluetooth 4.0+

GATT characteristics support

Battery level indication (recommended)

Adding New Sensors:

Find UUIDs for services/characteristics

Update BluetoothService class

Add parsing logic for new data format

🏗 App Architecture

Stack:

Language: Kotlin

UI: Jetpack Compose

Architecture: MVVM

Navigation: Compose Navigation

Bluetooth: Android Bluetooth API (BLE + Classic)

Permissions: Activity Result API

Structure:

app/
 └── src/main/java/com/example/soilhealthmonitoringapp/
      ├── ui/
      │    ├── components/   # Reusable UI
      │    ├── screens/      # Screens
      │    └── theme/        # Theming
      ├── bluetooth/         # Bluetooth logic
      ├── data/              # Models & repositories
      ├── utils/             # Utility classes
      └── MainActivity.kt

🧪 Testing
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

🔒 Permissions Explained
<!-- Android 12+ -->
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

<!-- Legacy (Android 11-) -->
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />


Why?

SCAN/BLUETOOTH: Discover nearby devices

CONNECT: Establish connections

ACCESS_FINE_LOCATION: Required by Android for Bluetooth discovery

⚠️ Known Issues

Android 12+: Some devices require manual permission granting

Battery Optimization: May interfere with background data collection

Sensor Compatibility: Some generic sensors may not be fully supported

🤝 Contributing

Fork repo

Create branch → git checkout -b feature/amazing-feature

Commit → git commit -m "Add amazing feature"

Push → git push origin feature/amazing-feature

Open Pull Request

Guidelines:

Follow Kotlin style guide

Use Jetpack Compose best practices

Write unit tests for new features

Update documentation

📜 License

This project is licensed under the MIT License – see the LICENSE
 file.

🙌 Acknowledgments

Android Bluetooth documentation

Jetpack Compose community

Open-source soil monitoring projects

Contributors & testers

📬 Contact

Developer: Pranav Yadav

Email: pranav.2001y@gmail.com

GitHub: @pranav50227

Project Link: Soil Health Monitoring App
