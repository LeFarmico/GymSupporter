import com.lefarmico.buildsrc.Base
import com.lefarmico.buildsrc.Deps

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
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
            isMinifyEnabled = true
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(project(":domain"))
    implementation(project(":core"))

    implementation(Deps.Androidx.lifecycleExtensions)
    implementation(Deps.Androidx.appcompat)

    implementation(Deps.Ktx.core)
    implementation(Deps.Ktx.legacySup)
    implementation(Deps.Ktx.fragment)
    implementation(Deps.Ktx.liveData)
    implementation(Deps.Ktx.liveDataCore)
    implementation(Deps.Ktx.viewModel)

    // Tests
    androidTestImplementation(Deps.Test.junit)

    // Core
    implementation(Deps.Androidx.constraintLayout)
    implementation(Deps.Ktx.legacySup)

    // Views
    implementation(Deps.Androidx.cardView)
    implementation(Deps.Androidx.recyclerView)
    implementation(Deps.Androidx.material)

    // GSON
    implementation(Deps.gson)

    // Dagger
    implementation(Deps.Dagger.dagger)
    implementation(Deps.Dagger.daggerAndroid)
    kapt(Deps.Dagger.daggerAndroidProcessor)
    kapt(Deps.Dagger.daggerCompiler)

    // Room
    implementation(Deps.Room.roomRuntime)
    implementation(Deps.Room.roomKtx)
    implementation(Deps.Room.roomRx)
    kapt(Deps.Room.roomCompiler)

    // RXJava
    implementation(Deps.RXJava.rxjava)
    implementation(Deps.RXJava.rxjavaAndroid)
    implementation(Deps.RXJava.rxjavaKotlin)
    implementation(Deps.RXJava.rxjavaRetrofitAdapter)

    // AdapterDelegates
    implementation(Deps.adapterDelegates)
}
