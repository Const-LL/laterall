package ru.otus.otuskotlin.markeplace.app.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.laterall.app.spring.config.LtrlAppSettings
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
//import ru.otus.otuskotlin.laterall.logging.common.MpLoggerProvider
//import ru.otus.otuskotlin.laterall.logging.jvm.mpLoggerLogback

@Suppress("unused")
@Configuration
class TaskConfig {
    @Bean
    fun processor(corSettings: LtrlCorSettings) = LtrlTaskProcessor(corSettings = corSettings)

//    @Bean
//    fun loggerProvider(): MpLoggerProvider = MpLoggerProvider { mpLoggerLogback(it) }

    @Bean
    fun corSettings(): LtrlCorSettings = LtrlCorSettings(
        loggerProvider = "test"//loggerProvider(),
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
