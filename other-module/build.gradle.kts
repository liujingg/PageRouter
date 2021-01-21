plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(property("COMPILE_SDK_VERSION").toString().toInt())

    defaultConfig {
        minSdkVersion(property("MIN_SDK_VERSION").toString().toInt())
        targetSdkVersion(property("TARGET_SDK_VERSION").toString().toInt())
        versionCode=property("VERSION_CODE").toString().toInt()
        versionName=property("VERSION_NAME").toString()

        javaCompileOptions {
            annotationProcessorOptions {
                argument("targetModuleName", "Other")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation ("androidx.appcompat:appcompat:${property("ANDROIDX_APPCOMPAT_VERSION")}")
    annotationProcessor(project(":router-compiler"))
    implementation(project(":router"))
}
