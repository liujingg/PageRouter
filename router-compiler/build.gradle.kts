plugins {
    id("java")
    id("maven")
    id("com.github.panpf.bintray-publish")
}
apply(from = "../pom-evaluator.gradle")

dependencies {
    implementation("com.squareup:javapoet:${property("JAVAPOET_VERSION")}")
    implementation(project(":router-annotation"))
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

