plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.coroutines.core)

    // transport models
    implementation(project(":laterall-common"))
//    implementation(project(":laterall-api-log1"))
    implementation(project(":laterall-biz"))
}