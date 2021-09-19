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

    implementation(com.lefarmico.buildsrc.Deps.Androidx.lifecycleExtensions)
    implementation(com.lefarmico.buildsrc.Deps.Androidx.appcompat)

    implementation(com.lefarmico.buildsrc.Deps.Ktx.core)
    implementation(com.lefarmico.buildsrc.Deps.Ktx.legacySup)
    implementation(com.lefarmico.buildsrc.Deps.Ktx.fragment)
    implementation(com.lefarmico.buildsrc.Deps.Ktx.liveData)
    implementation(com.lefarmico.buildsrc.Deps.Ktx.liveDataCore)
    implementation(com.lefarmico.buildsrc.Deps.Ktx.viewModel)

    // Tests
    androidTestImplementation(com.lefarmico.buildsrc.Deps.Test.junit)

    // Core
    implementation(com.lefarmico.buildsrc.Deps.Androidx.constraintLayout)
    implementation(com.lefarmico.buildsrc.Deps.Ktx.legacySup)

    // Views
    implementation(com.lefarmico.buildsrc.Deps.Androidx.cardView)
    implementation(com.lefarmico.buildsrc.Deps.Androidx.recyclerView)
    implementation(com.lefarmico.buildsrc.Deps.Androidx.material)

    // Dagger
    implementation(com.lefarmico.buildsrc.Deps.Dagger.dagger)
    implementation(com.lefarmico.buildsrc.Deps.Dagger.daggerAndroid)
    annotationProcessor(com.lefarmico.buildsrc.Deps.Dagger.daggerAndroidProcessor)

    // RXJava
    implementation(com.lefarmico.buildsrc.Deps.RXJava.rxjava)
    implementation(com.lefarmico.buildsrc.Deps.RXJava.rxjavaAndroid)
    implementation(com.lefarmico.buildsrc.Deps.RXJava.rxjavaKotlin)
    implementation(com.lefarmico.buildsrc.Deps.RXJava.rxjavaRetrofitAdapter)
}
