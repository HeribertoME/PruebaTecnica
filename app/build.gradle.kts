plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.pruebatecnica"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pruebatecnica"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.navigationFragmentKtx)
    implementation(libs.navigationUiKtx)
    implementation(libs.glide)
    implementation(libs.androidx.swiperefreshlayout)
    kapt(libs.glide.compiler)
    implementation(libs.lifecycleLivedataKtx)
    implementation(libs.lifecycleViewmodelKtx)
    implementation(libs.retrofit)
    implementation(libs.retrofitConverterGson)
    implementation(libs.hiltAndroid)
    kapt(libs.hiltAndroidCompiler)
    implementation(libs.coroutines.android)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.paging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}