package ru.otus.otuskotlin.laterall.app.rabbit

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.laterall.app.rabbit.config.LtrlAppSettings
import ru.otus.otuskotlin.laterall.app.rabbit.config.RabbitConfig
import ru.otus.otuskotlin.laterall.app.rabbit.mappers.fromArgs
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
//import ru.otus.otuskotlin.laterall.logging.common.MpLoggerProvider
//import ru.otus.otuskotlin.laterall.logging.jvm.mpLoggerLogback

fun main(vararg args: String) = runBlocking {
    val appSettings = LtrlAppSettings(
        rabbit = RabbitConfig.fromArgs(*args),
        corSettings = LtrlCorSettings()//there must be logger
    )
    val app = RabbitApp(appSettings = appSettings, this)
    app.start()
}
