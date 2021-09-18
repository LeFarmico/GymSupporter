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
    implementation(project(":data"))
    api(project(":domain"))
    implementation(project(":presentation"))

    // Tests
    androidTestImplementation(Deps.UiTest.junit)
    androidTestImplementation(Deps.UiTest.espresso)
    androidTestImplementation(Deps.UiTest.espressoContrib)

    // Core
    implementation(Deps.Ktx.core)
    implementation(Deps.Ktx.legacySup)

    // Dagger
    implementation(Deps.Dagger.dagger)
    implementation(Deps.Dagger.daggerAndroid)
    kapt(Deps.Dagger.daggerAndroidProcessor)
    kapt(Deps.Dagger.daggerCompiler)
}
