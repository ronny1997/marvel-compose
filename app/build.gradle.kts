import com.google.firebase.appdistribution.gradle.firebaseAppDistribution

@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.distribution)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.kotlin.kapt)
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        namespace = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.readVersionCode(rootProject.projectDir.path)
        versionName = AppConfig.readVersionName(rootProject.projectDir.path)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    /*signingConfigs {
        getByName("debug") {
            keyAlias = " "
            keyPassword = " "
            storeFile = file("${project.rootDir}${File.separator}**.keystore")
            storePassword = "android"
        }
        create("release") {
            keyAlias = " "
            keyPassword = " "
            storeFile =
                file("${project.rootDir}${File.separator}**.jks")
            storePassword = "Inn0cv2023!"
        }
    }*/

    buildTypes {
        getByName("release") {
            isTestCoverageEnabled = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            //signingConfig = signingConfigs.getByName("release")
            firebaseAppDistribution {
                releaseNotes = "QA-VERSION"
                groups = "testApps"
            }
        }
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
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(ModuleDependencies.coreModule))
    implementation(project(ModuleDependencies.dataModule))
    implementation(project(ModuleDependencies.domainModule))
    implementation(project(ModuleDependencies.uiModule))
    androidTestImplementation(libs.compose.testing)
    testImplementation(libs.junit)

    //ktor
    implementation(libs.ktor)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
}