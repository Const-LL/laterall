package  ru.otus.otuskotlin.laterall.app.spring.config

import ru.otus.otuskotlin.laterall.app.common.ILtrlAppSettings

import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings

data class LtrlAppSettings(
    override val corSettings: LtrlTaskProcessor,
    override val processor: LtrlCorSettings,
): ILtrlAppSettings