plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.notes_hoodlab"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.notes_hoodlab"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("io.realm.kotlin:library-base:1.6.0")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Coroutine Lifecycle Scopes
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.0")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.5.0")
    implementation("androidx.compose.runtime:runtime-rxjava3:1.5.0")
    // Compose dependencies
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("com.google.accompanist:accompanist-permissions:0.18.0")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.material:material-icons-extended:1.5.0")
    implementation("com.marosseleng.android:compose-material3-datetime-pickers:0.6.0")
    implementation("androidx.paging:paging-compose:3.2.0")
    implementation("com.google.accompanist:accompanist-flowlayout:0.17.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")
    kapt("com.google.dagger:hilt-compiler:2.47")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Media Player
    implementation("androidx.media:media:1.6.0")
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation("com.google.android.exoplayer:extension-mediasession:2.19.1")
    implementation("androidx.media3:media3-common:1.1.1")
    implementation("androidx.media3:media3-ui:1.1.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    //noinspection KaptUsageInsteadOfKsp
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    //Kotlin sınıflarını JSON formatına ve JSON verilerini Kotlin sınıflarına dönüştürmek için kullanılır.
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    //JSON verilerini Java nesnelerine  ve Java nesnelerini JSON verilere dönüştürmek için kullanılan Gson kütüphanesini içerir
    implementation("com.google.code.gson:gson:2.10.1")

    // timber log
    implementation("com.jakewharton.timber:timber:5.0.1")

    // coil with animation
    implementation("io.coil-kt:coil:2.2.2")
    implementation("com.github.skydoves:landscapist-coil:2.2.3")
    implementation("com.github.skydoves:landscapist-glide:2.1.0")
    implementation("com.github.skydoves:landscapist-placeholder:2.2.3")
    implementation("com.github.skydoves:landscapist-animation:2.2.3")
    implementation("com.github.skydoves:landscapist-transformation:2.2.3")
    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:2.5.2")

    implementation (platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")

    /*
        // Test and Debug
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.3")
        debugImplementation("androidx.compose.ui:ui-tooling:1.4.3")
        debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.3")
        androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
        // For instrumentation tests Dagger
        androidTestImplementation("com.google.dagger:hilt-android-testing:2.46.1")
        // For local unit tests Dagger
        testImplementation("com.google.dagger:hilt-android-testing:2.46.1")
        kaptTest("com.google.dagger:hilt-compiler:2.47")
        kaptAndroidTest("com.google.dagger:hilt-compiler:2.47")

     */

}
kapt {
    correctErrorTypes = true
}

