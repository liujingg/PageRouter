plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(property("COMPILE_SDK_VERSION").toString().toInt())
    defaultConfig {
        applicationId = "com.liujing.pagerouter.example"
        minSdkVersion(property("MIN_SDK_VERSION").toString().toInt())
        targetSdkVersion(property("TARGET_SDK_VERSION").toString().toInt())
        versionCode = property("VERSION_CODE").toString().toInt()
        versionName = property("VERSION_NAME").toString()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${property("KOTLIN_VERSION")}")
    implementation("androidx.appcompat:appcompat:${property("ANDROIDX_APPCOMPAT_VERSION")}")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation(project(":other-module"))
    implementation(project(":router"))
    kapt(project(":router-compiler"))
}
