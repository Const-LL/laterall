plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":laterall-common"))
    implementation(project(":laterall-stubs"))
    implementation(project(":laterall-repo-common"))
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.test)

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    implementation(kotlin("test"))
}
