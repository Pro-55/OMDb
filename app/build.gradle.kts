plugins {
    alias(libs.plugins.android.gradle)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
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
            manifestPlaceholders.apply {
                put("FBAppId", project.property("DEV_FB_APP_ID") as String)
                put("FBClientToken", project.property("DEV_FB_CLIENT_TOKEN") as String)
            }
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
            manifestPlaceholders.apply {
                put("FBAppId", project.property("PROD_FB_APP_ID") as String)
                put("FBClientToken", project.property("PROD_FB_CLIENT_TOKEN") as String)
            }
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

    // Core
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)

    // Architecture Components Lifecycle Extensions
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.common.java8)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization)

    // Ktor
    implementation(libs.ktor.core)
    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.logging)

    // Coil
    implementation(libs.coil)

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
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    // Facebook
    implementation(libs.facebook.android.sdk)

    // Analytics
    implementation(projects.analytics)

    // Test
    testImplementation(libs.junit)
    testImplementation(projects.analyticsDummy)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(projects.analyticsDummy)
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