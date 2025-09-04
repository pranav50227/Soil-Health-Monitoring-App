# Soil-Health-Monitoring-App
Soil Health Monitoring App
A comprehensive Android application for monitoring soil health parameters using Bluetooth-enabled sensors. Built with Jetpack Compose and modern Android development practices.
Features

Real-time Soil Monitoring: Monitor pH, moisture, temperature, and nutrient levels
Bluetooth Connectivity: Seamlessly connect to Bluetooth-enabled soil sensors
Modern UI: Built with Jetpack Compose for a smooth user experience
Navigation: Multi-screen navigation with bottom navigation bar
Permission Management: Automatic handling of Bluetooth and location permissions
Data Visualization: Real-time charts and graphs for sensor data

Screenshots
Add your app screenshots here
Requirements
System Requirements

Android Version: API 21 (Android 5.0) and above
Target SDK: API 35 (Android 15)
Bluetooth: Bluetooth 4.0+ (BLE support recommended)
Permissions: Location and Bluetooth permissions required

Hardware Requirements

Android device with Bluetooth capability
Compatible soil monitoring sensors (see Compatible Sensors section)

Installation
Option 1: Install from APK

Download the latest APK from the Releases page
Enable "Install from Unknown Sources" in your device settings
Install the APK file

Option 2: Build from Source

Clone the repository:
bashgit clone https://github.com/your-repo/soil-health-monitoring-app.git
cd soil-health-monitoring-app

Open the project in Android Studio
Sync the project and resolve dependencies
Build and run the app:
bash./gradlew assembleDebug


Bluetooth Setup
Prerequisites

Ensure your Android device has Bluetooth enabled
Grant necessary permissions when prompted by the app

Supported Bluetooth Profiles

Bluetooth Low Energy (BLE): Recommended for battery efficiency
Classic Bluetooth: For legacy sensor compatibility

Permissions Required
The app requires the following permissions for Bluetooth functionality:
Android 12+ (API 31+)

BLUETOOTH_SCAN: To discover nearby Bluetooth devices
BLUETOOTH_CONNECT: To connect to Bluetooth devices
ACCESS_FINE_LOCATION: Required for Bluetooth device discovery

Android 11 and below (API 30-)

BLUETOOTH: Basic Bluetooth functionality
BLUETOOTH_ADMIN: Bluetooth administration
ACCESS_FINE_LOCATION: Required for Bluetooth device discovery

Bluetooth Pairing Process

Enable Bluetooth: The app will prompt you to enable Bluetooth if it's disabled
Grant Permissions: Allow location and Bluetooth permissions when requested
Scan for Devices: Navigate to the "Devices" screen and tap "Scan"
Select Sensor: Choose your soil monitoring sensor from the discovered devices list
Pair Device: Follow the pairing instructions (PIN may be required)
Start Monitoring: Once connected, sensor data will be displayed in real-time

Troubleshooting Bluetooth Issues
Connection Problems

Device not found:

Ensure the sensor is in pairing mode
Check if the sensor is within range (typically 10 meters)
Restart Bluetooth on your phone


Connection fails:

Clear Bluetooth cache: Settings > Apps > Bluetooth > Storage > Clear Cache
Unpair and re-pair the device
Restart both the app and the sensor


Intermittent disconnections:

Check battery levels on both devices
Minimize interference from other Bluetooth devices
Keep devices within optimal range



Permission Issues

If permissions are denied, go to Settings > Apps > Soil Health App > Permissions
Enable Location and Nearby Devices permissions
Restart the app after granting permissions

Compatible Sensors
The app is designed to work with various soil monitoring sensors. Currently supported:
Recommended Sensors

Generic BLE Soil Sensors: Most Bluetooth-enabled soil pH/moisture sensors
Arduino-based Sensors: Custom sensors with Bluetooth modules
ESP32/ESP8266 Sensors: DIY sensors with WiFi/Bluetooth capability

Sensor Requirements

Bluetooth 4.0+ support
Standard GATT characteristics for sensor data
Battery level indication (recommended)

Adding New Sensors
To add support for a new sensor type:

Identify the sensor's Bluetooth service and characteristic UUIDs
Update the BluetoothService class with the new UUIDs
Add parsing logic for the sensor's data format
Test thoroughly with the new sensor

App Architecture
Technology Stack

Language: Kotlin
UI Framework: Jetpack Compose
Architecture: MVVM (Model-View-ViewModel)
Navigation: Compose Navigation
Bluetooth: Android Bluetooth API with BLE support
Permissions: Activity Result API

Project Structure
app/
├── src/main/java/com/example/soilhealthmonitoringapp/
│   ├── ui/
│   │   ├── components/          # Reusable UI components
│   │   ├── screens/            # App screens
│   │   └── theme/              # App theming
│   ├── bluetooth/              # Bluetooth functionality
│   ├── data/                   # Data models and repositories
│   ├── utils/                  # Utility classes
│   └── MainActivity.kt         # Main activity
├── src/main/res/
│   ├── layout/                 # XML layouts (if any)
│   ├── values/                 # Strings, colors, styles
│   └── drawable/               # App icons and images
└── AndroidManifest.xml         # App permissions and components
Development
Prerequisites

Android Studio Arctic Fox (2020.3.1) or later
JDK 11 or later
Android SDK with API 35
Git

Setup Development Environment

Clone the repository
Open in Android Studio
Install required SDK components when prompted
Enable USB Debugging on your test device
Run the app in debug mode

Building Release APK
bash# Generate signed APK
./gradlew assembleRelease

# Install on connected device
adb install -r app/build/outputs/apk/release/app-release.apk
Testing
bash# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
Permissions Explained
Required Permissions
xml<!-- Bluetooth permissions for API 31+ -->
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />

<!-- Location permission (required for Bluetooth device discovery) -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

<!-- Legacy Bluetooth permissions (API 30 and below) -->
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
Why These Permissions Are Needed

BLUETOOTH_SCAN/BLUETOOTH: To discover nearby soil sensors
BLUETOOTH_CONNECT: To establish connection with sensors
ACCESS_FINE_LOCATION: Required by Android for Bluetooth device discovery (privacy measure)

Known Issues
Android 12+ Issues

Some devices may require manual permission granting in system settings
Battery optimization may affect Bluetooth connectivity (add app to battery whitelist)

Sensor Compatibility

Not all generic Bluetooth sensors may be compatible
Custom sensors may require code modifications

Performance

Continuous Bluetooth scanning may impact battery life
Background data collection may be limited by Android's battery optimization

Contributing
We welcome contributions! Please follow these guidelines:

Fork the repository
Create a feature branch (git checkout -b feature/amazing-feature)
Commit your changes (git commit -m 'Add amazing feature')
Push to the branch (git push origin feature/amazing-feature)
Open a Pull Request

Development Guidelines

Follow Kotlin coding conventions
Use Jetpack Compose best practices
Add unit tests for new features
Update documentation as needed

License
This project is licensed under the MIT License - see the LICENSE file for details.
Support
Getting Help

Issues: Report bugs and request features on GitHub Issues
Documentation: Check the Wiki for detailed guides
Community: Join our Discussions for community support

Common Solutions

App crashes on startup: Check if all permissions are granted
Bluetooth not working: Verify device compatibility and permissions
Sensor not connecting: Ensure sensor is in pairing mode and within range

Changelog
Version 1.0.0 (Latest)

Initial release
Bluetooth connectivity for soil sensors
Real-time data monitoring
Modern Compose UI
Multi-sensor support

Acknowledgments

Android Bluetooth documentation and examples
Jetpack Compose community
Open source soil monitoring projects
Contributors and beta testers

Contact

Developer: Your Name
Email: your.email@example.com
GitHub: @yourusername
Project Link: https://github.com/your-repo/soil-health-monitoring-app


Made with ❤️ for sustainable agriculture and soil health monitoring
