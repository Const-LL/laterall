package ru.otus.otuskotlin.laterall.app.spring.config

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.otus.otuskotlin.laterall.repo.postgresql.SqlProperties

@ConfigurationProperties(prefix = "psql")
data class TaskConfigPostgres(
    var host: String = "localhost",
    var port: Int = 5432,
    var user: String = "postgres",
    var password: String = "postgres",
    var database: String = "laterall_tasks",
    var schema: String = "public",
    var table: String = "tasks",
) {
    val psql: SqlProperties = SqlProperties(
        host = host,
        port = port,
        user = user,
        password = password,
        database = database,
        schema = schema,
        table = table,
    )
}
