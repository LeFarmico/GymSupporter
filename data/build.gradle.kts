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
}

dependencies {

    api(project(":domain"))

    // Tests
    androidTestImplementation(Deps.UiTest.junit)
    androidTestImplementation(Deps.UiTest.espresso)
    androidTestImplementation(Deps.UiTest.espressoContrib)

    // RXJava
    implementation(Deps.RXJava.rxjava)
    implementation(Deps.RXJava.rxjavaAndroid)
    implementation(Deps.RXJava.rxjavaKotlin)
    implementation(Deps.RXJava.rxjavaRetrofitAdapter)

    // Room
    implementation(Deps.Room.roomRuntime)
    implementation(Deps.Room.roomKtx)
    implementation(Deps.Room.roomRx)
    kapt(Deps.Room.roomCompiler)

    // GSON
    implementation(Deps.gson)

    // Dagger
    implementation(Deps.Dagger.dagger)
    implementation(Deps.Dagger.daggerAndroid)
    kapt(Deps.Dagger.daggerCompiler)
    kapt(Deps.Dagger.daggerAndroidProcessor)
}
