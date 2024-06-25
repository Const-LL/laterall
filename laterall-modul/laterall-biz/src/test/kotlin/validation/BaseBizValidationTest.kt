package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.LtrlCommand

abstract class BaseBizValidationTest {
    protected abstract val command: LtrlCommand
    private val settings by lazy { LtrlCorSettings() }
    protected val processor by lazy { LtrlTaskProcessor(settings) }
}
