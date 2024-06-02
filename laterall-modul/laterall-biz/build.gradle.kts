plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.cor)
    implementation(project(":laterall-common"))
    implementation(project(":laterall-stubs"))
}

