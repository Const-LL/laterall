plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":laterall-common"))
//    implementation(project(":laterall-stubs"))
}

