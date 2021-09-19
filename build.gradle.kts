// Top-level build file where you can add configuration options common to all sub-projects/modules.
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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
