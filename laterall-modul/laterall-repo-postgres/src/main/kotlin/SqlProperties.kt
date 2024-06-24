package ru.otus.otuskotlin.laterall.repo.postgresql

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "laterall-pass",
    val database: String = "laterall_tasks",
    val schema: String = "public",
    val table: String = "tasks",
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}
