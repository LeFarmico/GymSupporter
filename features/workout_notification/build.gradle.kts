import com.lefarmico.buildsrc.Base
import com.lefarmico.buildsrc.Deps

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = Base.currentSDK

    defaultConfig {
        minSdk = Base.minSDK
        targetSdk = Base.currentSDK
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))

    implementation(Deps.Ktx.core)
    implementation(Deps.Ktx.legacySup)

    // Dagger
    implementation(Deps.Dagger.dagger)
    implementation(Deps.Dagger.daggerAndroid)
    implementation(Deps.Androidx.navigationRuntime)
    annotationProcessor(Deps.Dagger.daggerAndroidProcessor)

    // RXJava
    implementation(Deps.RXJava.rxjava)
    implementation(Deps.RXJava.rxjavaAndroid)
    implementation(Deps.RXJava.rxjavaKotlin)
}
