plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":laterall-common"))
    testImplementation(kotlin("test-junit"))
}
