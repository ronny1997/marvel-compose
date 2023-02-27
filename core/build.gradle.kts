@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.serialization)
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "DEVICE_LITERALS_ENDPOINT", "\"/api/Cache/GetDeviceLiterals\"")
        buildConfigField("String", "BASE_URL", "\"google.com\"")
    }


    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    with (libs) {
        api(androidx.core.ktx)
        api(app.compat)

        api("androidx.constraintlayout:constraintlayout-compose:1.0.1")

        api(lifecycle.runtime.ktx)
        api(activity.compose)
        api(androidx.core.ktx)
        api(compose.ui)
        api(compose.material)
        api(compose.animation)
        api(compose.material.icons)
        api(compose.tooling)
        api(gson)
        api(compose.accompanist)
        api(libs.room.ktx)

        api(androidx.lifecycle.viewmodel.ktx)

        api(timber)

        //navigation
        api(hilt.navigation.compose)

        //ktor
        api(bundles.ktor)

        api(kermit)

        api(kotlinx.datetime)
        androidTestImplementation(compose.testing)

        testImplementation(junit)
        androidTestImplementation(junit.ext)
        androidTestImplementation(androidx.test.espresso.core)
        debugImplementation(compose.testing.manifest)

        debugApi(custom.view)
        debugApi(custom.view.poolingcontainer)

        //DataStore
        api(androidx.datastore)

        implementation(google.guava)
    }

}