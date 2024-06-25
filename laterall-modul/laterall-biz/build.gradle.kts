plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.cor)
    implementation(project(":laterall-common"))
    implementation(project(":laterall-stubs"))
    implementation(project(":laterall-repo-common"))
    implementation(project(":laterall-repo-tests"))
    implementation(project(":laterall-repo-inmemory"))

    testImplementation(libs.coroutines.test)
    testImplementation(kotlin("test"))


}

