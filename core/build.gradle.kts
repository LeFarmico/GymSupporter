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

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "VERSION_NAME", "\"${defaultConfig.versionName}\"")
        }
        getByName("release") {
            buildConfigField("String", "VERSION_NAME", "\"${defaultConfig.versionName}\"")
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    api(project(":domain"))
    api(project(":navigation"))

    implementation(Deps.Androidx.appcompat)
    implementation(Deps.Androidx.recyclerView)
    implementation(Deps.Androidx.constraintLayout)
    implementation(Deps.Androidx.cardView)
    implementation(Deps.Androidx.material)
    implementation(Deps.Androidx.lifecycleExtensions)
    implementation(Deps.Androidx.viewpager2)
    implementation(Deps.Ktx.core)
    implementation(Deps.Ktx.fragment)
    implementation(Deps.Ktx.liveDataCore)
    implementation(Deps.Ktx.liveData)
    implementation(Deps.Ktx.viewModel)

    // RXJava
    implementation(Deps.RXJava.rxjava)
    implementation(Deps.RXJava.rxjavaAndroid)
    implementation(Deps.RXJava.rxjavaKotlin)

    // Dagger
    implementation(Deps.Dagger.dagger)
    implementation(Deps.Dagger.daggerAndroid)
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.preference:preference-ktx:1.1.1")
    annotationProcessor(Deps.Dagger.daggerAndroidProcessor)

    testImplementation(Deps.UiTest.espressoIdling)
    testImplementation(Deps.Test.junit)
}
