package ru.otus.otuskotlin.laterall.repo.postgresql

actual fun getEnv(name: String): String? = System.getenv(name)
