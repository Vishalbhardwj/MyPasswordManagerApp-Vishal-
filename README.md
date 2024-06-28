# Secure Password Manager

## Overview
Secure Password Manager is an Android application designed to help users securely store and manage their passwords. With an intuitive user interface and robust security features, this app ensures that your sensitive information remains protected.

## Screenshots

<img src="https://github.com/Vishalbhardwj/MyPasswordManagerApp-Vishal-/assets/139758910/f61807de-af69-4b9d-bc8a-fd2b857fbaa9" height="350">
<img src="https://github.com/Vishalbhardwj/MyPasswordManagerApp-Vishal-/assets/139758910/dbb298e4-0d83-4b38-b6f0-14e6bd49f173" width="220" height="350">
<img src="https://github.com/Vishalbhardwj/MyPasswordManagerApp-Vishal-/assets/139758910/2c0acfac-5eb2-4830-ac2f-fe007c919eda" height="350">
<img src="https://github.com/Vishalbhardwj/MyPasswordManagerApp-Vishal-/assets/139758910/ef9a1d57-8f56-4a0b-a5e0-1a4e0d542a35" height="350">




## Features
- **Database:** Utilizes a secure local database (Room Persistence) to store encrypted passwords locally on the device.
- **User Interface:** Designed a clean and intuitive UI for managing passwords.
- **Biometric Authentication:** Prompts users to authenticate using biometric (fingerprint) for added security upon opening the app.
- **Error Handling:** Properly handles errors and edge cases to ensure a smooth user experience.
- **Documentation:** Provides clear instructions on how to build, run, and use the application.






## Tech Stack
- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **Local Database:** Using Room persistence lib
- **Biometric:** Added FingerPrint

## Getting Started
To run the project locally, follow these steps:
1. Clone the repository: git clone https://github.com/Vishalbhardwj/MyPasswordManagerApp-Vishal-
3. Open the project in Android Studio.
4. Build the project and run it on an emulator or device.

## Dependencies
```kotlin
dependencies {
 // Core libraries
implementation(libs.androidx.core.ktx) // Core KTX library for Android extensions
implementation(libs.androidx.lifecycle.runtime.ktx) // Lifecycle runtime KTX for Android Lifecycle components

// Activity and Compose
implementation(libs.androidx.activity.compose) // Compose integration for Android activities
implementation(platform(libs.androidx.compose.bom)) // BOM for managing Compose dependencies versions
implementation(libs.androidx.ui) // Main UI library for Jetpack Compose
implementation(libs.androidx.ui.graphics) // Compose library for graphics-related components
implementation(libs.androidx.ui.tooling.preview) // Tooling library for Compose previews
implementation(libs.androidx.material3) // Material Design 3 components for Jetpack Compose
implementation(libs.androidx.material3.android) // Additional Material Design 3 components for Android

// Testing dependencies
testImplementation(libs.junit) // JUnit for unit testing
androidTestImplementation(libs.androidx.junit) // JUnit for Android instrumentation tests
androidTestImplementation(libs.androidx.espresso.core) // Espresso for Android UI testing
androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM for managing Compose dependencies versions in Android tests
androidTestImplementation(libs.androidx.ui.test.junit4) // UI testing library for Jetpack Compose with JUnit4

// Debug dependencies
debugImplementation(libs.androidx.ui.tooling) // Tooling library for debugging Compose layouts
debugImplementation(libs.androidx.ui.test.manifest) // Manifest configuration for Compose UI testing

// Biometric authentication
implementation("androidx.biometric:biometric:1.2.0-alpha05") // Library for biometric authentication

// Navigation
val nav_version = "2.7.7"
implementation("androidx.navigation:navigation-compose:$nav_version") // Navigation library for Jetpack Compose

// Room (local database)
implementation("androidx.room:room-runtime:2.6.1") // Room runtime library
annotationProcessor("androidx.room:room-compiler:2.6.1") // Annotation processor for Room
kapt("androidx.room:room-compiler:2.6.1") // Kapt processor for Room (for Kotlin projects)
implementation("androidx.room:room-ktx:2.6.1") // KTX extensions for Room

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2") // Coroutines library for Android

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0") // ViewModel library with KTX extensions

// CardView
implementation("androidx.cardview:cardview:1.0.0") // CardView library for displaying cards in the UI

// LiveData and Material3 for Compose
implementation("androidx.compose.runtime:runtime-livedata:1.5.1") // LiveData integration for Jetpack Compose
implementation("androidx.compose.material3:material3:1.1.1") // Material Design 3 components for Jetpack Compose
implementation("androidx.compose.material3:material3-window-size-class:1.1.1") // Ma


}


