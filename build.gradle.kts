// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath(kotlin(module = "gradle-plugin", version = "1.8.0"))
        classpath(kotlin(module = "serialization", version = "1.8.10"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.7")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}