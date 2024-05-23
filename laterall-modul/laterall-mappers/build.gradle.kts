plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":laterall-api"))
    implementation(project(":laterall-common"))
    testImplementation(kotlin("test-junit"))
}
