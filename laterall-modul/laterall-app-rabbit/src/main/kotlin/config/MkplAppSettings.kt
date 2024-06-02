package ru.otus.otuskotlin.laterall.app.rabbit.config

import ru.otus.otuskotlin.laterall.app.common.ILtrlAppSettings
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings

data class LtrlAppSettings(
    override val corSettings: LtrlCorSettings = LtrlCorSettings(),
    override val processor: LtrlTaskProcessor = LtrlTaskProcessor(corSettings),
    override val rabbit: RabbitConfig = RabbitConfig(),
    override val controllersConfig: RabbitExchangeConfiguration = RabbitExchangeConfiguration.NONE,
): ILtrlAppSettings, ILtrlAppRabbitSettings
