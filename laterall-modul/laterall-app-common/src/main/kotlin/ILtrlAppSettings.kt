package ru.otus.otuskotlin.laterall.app.common

import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings

interface ILtrlAppSettings {
    val processor: LtrlTaskProcessor
    val corSettings: LtrlCorSettings
}
