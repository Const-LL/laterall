rootProject.name = "laterall-modul"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}


include(":laterall-api")
include(":laterall-mappers")
include(":laterall-common")

include(":laterall-biz")
include(":laterall-stubs")


include(":laterall-app-spring")
include(":laterall-app-common")

include(":laterall-app-rabbit")

// DB
include(":laterall-repo-common")
include(":laterall-repo-inmemory")
include(":laterall-repo-stubs")
include(":laterall-repo-tests")
include(":laterall-repo-postgres")