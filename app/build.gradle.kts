plugins {
    apply {
        id("com.android.application")
        kotlin("android")
        kotlin("kapt")
        id("org.jetbrains.kotlin.plugin.parcelize")
        id("androidx.navigation.safeargs")
        id("com.google.gms.google-services")
        id("com.google.firebase.crashlytics")
        id("dagger.hilt.android.plugin")
    }
}

android {
    compileSdk = 31
    buildFeatures {
        dataBinding = true
    }
    defaultConfig {
        applicationId = "com.example.omdb"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
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
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
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
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10")
    implementation("androidx.core:core-ktx:1.7.0")

    // Material Design Components
    implementation("com.google.android.material:material:1.4.0")

    // Architecture Components Lifecycle Extensions
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.4.0")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.4.0")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    // Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // RxAndroid implementation
    implementation("io.reactivex:rxandroid:1.2.1")

    // RxTextViewChange
    implementation("com.jakewharton.rxbinding:rxbinding:1.0.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Retrofit GSON conversion
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    // Room
    implementation("androidx.room:room-runtime:2.4.0")
    kapt("androidx.room:room-compiler:2.4.0")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.4.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Hilt DI
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-compiler:2.38.1")

    // Hilt ViewModel
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:26.5.0"))
    // Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")
    // Crashlytics
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    // Cloud Messaging
    implementation("com.google.firebase:firebase-messaging-ktx")
    // Authentication
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:20.0.0")

    // Facebook
    implementation("com.facebook.android:facebook-android-sdk:11.2.0")

    // ProgressButton
    implementation("com.github.razir.progressbutton:progressbutton:2.1.0")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

}

kapt {
    correctErrorTypes = true
}