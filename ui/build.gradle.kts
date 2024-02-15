plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.mpapps.marvelcompose.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    implementation(project(":domain"))
    api(libs.bundles.androidx)
    api(libs.bundles.compose)
    implementation(libs.coroutines.playservices)
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.android)
    implementation(libs.guava)
    implementation(libs.gson)
    debugImplementation(libs.ui.tooling)
    kapt(libs.hilt.kapt)
    androidTestImplementation(libs.bundles.test)
}