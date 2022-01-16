import com.lefarmico.buildsrc.Deps

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = com.lefarmico.buildsrc.Base.currentSDK

    defaultConfig {
        minSdk = com.lefarmico.buildsrc.Base.minSDK
        targetSdk = com.lefarmico.buildsrc.Base.currentSDK
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features:workout_notification"))

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

    // Dagger
    implementation(Deps.Dagger.dagger)
    implementation(Deps.Dagger.daggerAndroid)
    annotationProcessor(Deps.Dagger.daggerAndroidProcessor)

    // RXJava
    implementation(Deps.RXJava.rxjava)
    implementation(Deps.RXJava.rxjavaAndroid)
    implementation(Deps.RXJava.rxjavaKotlin)
}
