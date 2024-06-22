plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version
dependencies {
    implementation(kotlin("stdlib-common"))
    api(libs.kotlinx.datetime)
}