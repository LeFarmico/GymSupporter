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

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "VERSION_NAME", "\"${defaultConfig.versionName}\"")
        }
        getByName("release") {
            buildConfigField("String", "VERSION_NAME", "\"${defaultConfig.versionName}\"")
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features:exercise_details"))
    implementation(project(":features:exercise_menu"))
    implementation(project(":features:home"))
    implementation(project(":features:workout"))
    implementation(project(":features:create_new_exercise"))

    // Tests
    androidTestImplementation(com.lefarmico.buildsrc.Deps.Test.junit)

    // Dagger
    implementation(com.lefarmico.buildsrc.Deps.Dagger.dagger)
    implementation(com.lefarmico.buildsrc.Deps.Dagger.daggerAndroid)
    kapt(com.lefarmico.buildsrc.Deps.Dagger.daggerCompiler)
    kapt(com.lefarmico.buildsrc.Deps.Dagger.daggerAndroidProcessor)
}
