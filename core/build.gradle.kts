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
        versionCode = Base.versionCode
        versionName = Base.versionName

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
//    api(project(":navigation"))

    api(Deps.Androidx.appcompat)
    api(Deps.Androidx.recyclerView)
    api(Deps.Androidx.constraintLayout)
    api(Deps.Androidx.cardView)
    api(Deps.Androidx.material)
    api(Deps.Androidx.lifecycleExtensions)
    api(Deps.Androidx.viewpager2)
    api(Deps.Ktx.core)
    api(Deps.Ktx.fragment)
    api(Deps.Ktx.liveDataCore)
    api(Deps.Ktx.liveData)
    api(Deps.Ktx.viewModel)
    api(Deps.Ktx.liveData)
    api(Deps.Ktx.viewModel)
    api(Deps.UiTest.espressoIdling)

    testImplementation(Deps.Test.junit)
}
