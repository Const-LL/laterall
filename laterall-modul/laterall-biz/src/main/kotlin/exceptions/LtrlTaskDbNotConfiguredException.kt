package ru.otus.otuskotlin.laterall.biz.exceptions

import ru.otus.otuskotlin.laterall.common.models.LtrlWorkMode

class LtrlTaskDbNotConfiguredException(val workMode: LtrlWorkMode): Exception(
    "Database is not configured properly for workmode $workMode"
)
