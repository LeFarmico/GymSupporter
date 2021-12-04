buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(com.lefarmico.buildsrc.BuildPlugins.gradle)
        classpath(com.lefarmico.buildsrc.BuildPlugins.kotlin)
        classpath(com.lefarmico.buildsrc.BuildPlugins.ktlint)
        classpath(com.lefarmico.buildsrc.BuildPlugins.extensions)

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.8.0")
        classpath("com.autonomousapps:dependency-analysis-gradle-plugin:0.78.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("com.autonomousapps.dependency-analysis")
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
