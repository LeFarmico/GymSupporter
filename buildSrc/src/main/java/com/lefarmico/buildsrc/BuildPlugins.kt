package com.lefarmico.buildsrc

object BuildPlugins {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
    const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"
    const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsGradle}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
}
