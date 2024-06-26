plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":laterall-common"))
    implementation(project(":laterall-repo-common"))
    implementation(project(":laterall-repo-tests"))
    implementation(libs.coroutines.core)
    implementation(libs.db.cache4k)
    implementation(libs.uuid)

    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit5"))
}