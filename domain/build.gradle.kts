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
}

dependencies {

    implementation(Deps.Ktx.core)
    implementation(Deps.Androidx.appcompat)

    // Tests
    androidTestImplementation(Deps.UiTest.junit)

    // RXJava
    implementation(Deps.RXJava.rxjava)
}
