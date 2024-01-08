plugins {
    alias(libs.plugins.android.gradle)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.omdb"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = getVersionCode()
        versionName = getVersionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
    signingConfigs {
        create("config") {
            storeFile = file(project.property("STORE_PATH") as String)
            storePassword = project.property("STORE_PASSWORD") as String
            keyAlias = project.property("KEY_ALIAS") as String
            keyPassword = project.property("KEY_PASSWORD") as String
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("config")
            buildConfigField(
                "String",
                "BaseUrl",
                project.property("DEV_URL") as String
            )
            buildConfigField(
                "String",
                "ApiKey",
                project.property("OMDB_API_KEY") as String
            )
            buildConfigField(
                "String",
                "GCP_CLIENT_ID",
                project.property("DEV_CLIENT_ID") as String
            )
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            signingConfig = signingConfigs.getByName("config")
            buildConfigField(
                "String",
                "BaseUrl",
                project.property("PROD_URL") as String
            )
            buildConfigField(
                "String",
                "ApiKey",
                project.property("OMDB_API_KEY") as String
            )
            buildConfigField(
                "String",
                "GCP_CLIENT_ID",
                project.property("PROD_CLIENT_ID") as String
            )
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = getJavaVersion()
        targetCompatibility = getJavaVersion()
    }
    kotlinOptions {
        jvmTarget = getJavaVersion().toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.androidx.core)

    // Material Design Components
    implementation(libs.material)

    // Architecture Components Lifecycle Extensions
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.common.java8)

    // Fragment
    implementation(libs.androidx.fragment)

    // Navigation Component
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Constraint Layout
    implementation(libs.androidx.constraintlayout)

    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // RxAndroid implementation
    implementation(libs.rxAndroid)

    // RxTextViewChange
    implementation(libs.rxBinding)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization)

    // Ktor
    implementation(libs.ktor.core)
    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.logging)

    // Glide
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Hilt DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    // Google Analytics
    implementation(libs.firebase.analytics)
    // Crashlytics
    implementation(libs.firebase.crashlytics)
    // Cloud Messaging
    implementation(libs.firebase.messaging)
    // Authentication
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    // Facebook
    implementation(libs.facebook.android.sdk)

    // ProgressButton
    implementation(libs.progressbutton)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.espresso.core)
}

fun getVersionCode(): Int {
    val major = libs.versions.major.get().toInt() * 100000
    val minor = libs.versions.minor.get().toInt() * 100
    val hotfix = libs.versions.hotfix.get().toInt()
    return 100000000 + major + minor + hotfix
}

fun getVersionName(): String {
    val major = libs.versions.major.get()
    val minor = libs.versions.minor.get()
    val hotfix = libs.versions.hotfix.get()
    return "$major.$minor.$hotfix"
}

fun getJavaVersion(): JavaVersion = JavaVersion.VERSION_17