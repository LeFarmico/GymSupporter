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
}

dependencies {

    implementation(Deps.Ktx.core)
    implementation(Deps.Androidx.appcompat)

    // Tests
    androidTestImplementation(Deps.UiTest.junit)
    androidTestImplementation(Deps.UiTest.espresso)
    androidTestImplementation(Deps.UiTest.espressoContrib)

    // RXJava
    implementation(Deps.RXJava.rxjava)
    implementation(Deps.RXJava.rxjavaAndroid)
    implementation(Deps.RXJava.rxjavaKotlin)
    implementation(Deps.RXJava.rxjavaRetrofitAdapter)
}
