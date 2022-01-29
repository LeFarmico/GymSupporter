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

        classpath(com.lefarmico.buildsrc.BuildPlugins.crashlyticsGradle)
        classpath(com.lefarmico.buildsrc.BuildPlugins.googleServices)
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
    }
}
