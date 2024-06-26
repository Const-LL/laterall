package ru.otus.otuskotlin.laterall.app.spring.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.laterall.repo.stubs.TaskRepoStub
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.repo.IRepoTask
//import ru.otus.otuskotlin.laterall.logging.common.MpLoggerProvider
//import ru.otus.otuskotlin.laterall.logging.jvm.mpLoggerLogback
import ru.otus.otuskotlin.laterall.repo.inmemory.TaskRepoInMemory
import ru.otus.otuskotlin.laterall.repo.postgresql.RepoTaskSql

@Suppress("unused")
@EnableConfigurationProperties(TaskConfigPostgres::class)
@Configuration
class TaskConfig(val postgresConfig: TaskConfigPostgres) {
    @Bean
    fun processor(corSettings: LtrlCorSettings) = LtrlTaskProcessor(corSettings = corSettings)

//    @Bean
//    fun loggerProvider(): MpLoggerProvider = MpLoggerProvider { mpLoggerLogback(it) }
    @Bean
    fun testRepo(): IRepoTask = TaskRepoInMemory()

    @Bean
    fun prodRepo(): IRepoTask = RepoTaskSql(postgresConfig.psql)

    @Bean
    fun stubRepo(): IRepoTask = TaskRepoStub()

    @Bean
    fun corSettings(): LtrlCorSettings = LtrlCorSettings(
        loggerProvider = "test",//loggerProvider(),
        repoTest = testRepo(),
        repoProd = prodRepo(),
        repoStub = stubRepo(),
    )

    @Bean
    fun appSettings(
        corSettings: LtrlCorSettings,
        processor: LtrlTaskProcessor,
    ) = LtrlAppSettings(
        corSettings = corSettings,
        processor = processor,
    )
}
