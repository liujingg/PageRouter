plugins {
    id("com.android.library")
    id("maven")
    id("kotlin-android")
    id("com.github.panpf.bintray-publish")
    id("kotlin-kapt")
}
apply(from = "../pom-evaluator.gradle")

android {
    compileSdkVersion(property("COMPILE_SDK_VERSION").toString().toInt())

    defaultConfig {
        minSdkVersion(property("MIN_SDK_VERSION").toString().toInt())
        targetSdkVersion(property("TARGET_SDK_VERSION").toString().toInt())
        versionCode=property("VERSION_CODE").toString().toInt()
        versionName=property("VERSION_NAME").toString()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    api("androidx.fragment:fragment:${property("ANDROIDX_FRAGMENT_VERSION")}")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${property("KOTLIN_VERSION")}")
    api(project(":router-annotation"))
}

configure<com.github.panpf.bintray.publish.PublishExtension> {
    userOrg = "liujing"
    groupId = property("POM_GROUP_ID").toString()
    publishVersion = property("VERSION_NAME").toString()
    artifactId = "router-compiler"
    repoName = "pagerouter"
    website = "https://github.com/liujingg/PageRouter"
    desc = "A lightweight router framework for Android applications"
}

