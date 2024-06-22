rootProject.name = "laterall"

includeBuild("laterall-less")
includeBuild("laterall-modul")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}