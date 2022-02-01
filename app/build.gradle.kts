import com.lefarmico.buildsrc.Base
import com.lefarmico.buildsrc.Deps

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = Base.currentSDK

    defaultConfig {
        applicationId = "com.lefarmico.donetime"
        versionCode = Base.versionCode
        versionName = Base.versionName
        minSdk = Base.minSDK
        targetSdk = Base.currentSDK

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-debug-rules.pro"
            )
            testProguardFile("proguard-test-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        animationsDisabled = true
    }
}

dependencies {
    // Modules
    implementation(project(":core"))
    implementation(project(":navigation"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":features"))
    implementation(project(":features:exercise_details"))
    implementation(project(":features:exercise_menu"))
    implementation(project(":features:home"))
    implementation(project(":features:workout"))
    implementation(project(":features:create_new_exercise"))
    implementation(project(":features:detailed_record_workout"))
    implementation(project(":features:settings_screen"))
    implementation(project(":features:workout_notification"))

    // Tests
    androidTestImplementation(Deps.UiTest.junit)
    androidTestImplementation(Deps.UiTest.espresso)
    androidTestImplementation(Deps.UiTest.espressoContrib)

    // Core
    implementation(Deps.Ktx.core)
    implementation(Deps.Ktx.legacySup)
    implementation(Deps.Ktx.fragment)
    implementation(Deps.Ktx.liveData)
    implementation(Deps.Ktx.liveDataCore)
    implementation(Deps.Ktx.viewModel)
    implementation(Deps.Androidx.constraintLayout)

    // Dagger
    implementation(Deps.Dagger.dagger)
    implementation(Deps.Dagger.daggerAndroid)
    kapt(Deps.Dagger.daggerCompiler)
    kapt(Deps.Dagger.daggerAndroidProcessor)

    // Navigation
    implementation(Deps.Ktx.navigationUi)
    implementation(Deps.Ktx.navigationFragment)

    // Firebase
    implementation(Deps.Firebase.firebaseBom)
    implementation(Deps.Firebase.analytics)
    implementation(Deps.Firebase.crashlytics)

    // RXJava
    implementation(Deps.RXJava.rxjava)
    implementation(Deps.RXJava.rxjavaAndroid)
    implementation(Deps.RXJava.rxjavaKotlin)
}
