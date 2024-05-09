package ru.otus.otuskotlin.laterall.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class BuildPluginMultiplatform : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        group = rootProject.group
        version = rootProject.version
    }
}