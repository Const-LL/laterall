plugins {
    id("build-jvm")
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(libs.coroutines.test)
    testImplementation(kotlin("test"))
}