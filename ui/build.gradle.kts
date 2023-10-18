plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mpapps.marvelcompose.ui"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {

    api ("androidx.activity:activity-compose:1.5.1")
    api ("androidx.compose.material:material:1.3.1")
    api ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    api ("androidx.navigation:navigation-compose:2.6.0")
    api ("androidx.core:core-ktx:1.6.0")
    api ("androidx.appcompat:appcompat:1.6.1")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    testImplementation ("androidx.test.ext:junit-ktx:1.1.3")
    testImplementation ("androidx.test:core-ktx:1.4.0")

}