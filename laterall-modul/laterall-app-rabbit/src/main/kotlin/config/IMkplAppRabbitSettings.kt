package ru.otus.otuskotlin.laterall.app.rabbit.config

import ru.otus.otuskotlin.laterall.app.common.ILtrlAppSettings

interface ILtrlAppRabbitSettings: ILtrlAppSettings {
    val rabbit: RabbitConfig
    val controllersConfig: RabbitExchangeConfiguration
}
