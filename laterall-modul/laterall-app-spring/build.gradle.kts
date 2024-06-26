plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencies)
    alias(libs.plugins.spring.kotlin)
    id("build-jvm")
}

dependencies {
    implementation(libs.spring.actuator)
    implementation(libs.spring.webflux)
    implementation(libs.spring.webflux.ui)
    implementation(libs.jackson.kotlin)
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.reactor)
    implementation(libs.coroutines.reactive)

    // Внутренние модели
    implementation(project(":laterall-common"))
    implementation(project(":laterall-app-common"))

    //  api
    implementation(project(":laterall-api"))
    implementation(project(":laterall-mappers"))

    // biz
    implementation(project(":laterall-biz"))

    implementation(project(":laterall-stubs"))

    //db common + inmemory
    implementation(project(":laterall-repo-common"))
    implementation(project(":laterall-repo-stubs"))
    implementation(project(":laterall-repo-inmemory"))

    //db-pg
    implementation(project(":laterall-repo-postgres"))


    // tests
    testImplementation(kotlin("test"))
    testImplementation(libs.spring.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.spring.mockk)
}

tasks {
    withType<ProcessResources> {
        val files = listOf("spec").map {
            rootProject.ext[it]
        }
        from(files) {
            into("/static")
            filter {
                // Устанавливаем версию в сваггере
                it.replace("\${VERSION_APP}", project.version.toString())
            }

        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
//    environment("LTRLTASKS_DB", "test_db")
}
