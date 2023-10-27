plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.mpapps.marvelcompose.framework"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(libs.coroutines)
    implementation(libs.kotlin.reflect)
    implementation(libs.bundles.ktor)
    implementation(libs.hilt.android)
    kapt(libs.hilt.kapt)
    testImplementation(libs.bundles.test)
}