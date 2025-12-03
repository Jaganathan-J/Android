buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        // Robust KSP fallback for Kotlin 2.2.21
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.21-1.0.29")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.57.2")
    }
}

plugins {
    id("com.android.application") version "8.13.1" apply false
    id("com.android.library") version "8.13.1" apply false
    id("org.jetbrains.kotlin.android") version "2.2.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.21" apply false
}