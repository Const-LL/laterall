plugins {
    id("build-jvm")
    application
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.muschko.java)
}

application {
    mainClass.set("ru.otus.otuskotlin.laterall.app.rabbit.ApplicationKt")
}

dependencies {

    implementation(kotlin("stdlib"))

    implementation(libs.rabbitmq.client)
    implementation(libs.jackson.databind)
//    implementation(libs.logback)
    implementation(libs.coroutines.core)

    implementation(project(":laterall-common"))
    implementation(project(":laterall-app-common"))
//    implementation("ru.otus.otuskotlin.laterall.libs:ok-laterall-lib-logging-logback")

    implementation(project(":laterall-api"))
    implementation(project(":laterall-mappers"))

    implementation(project(":laterall-biz"))
    implementation(project(":laterall-stubs"))

    testImplementation(libs.testcontainers.rabbitmq)
    testImplementation(kotlin("test"))
}
