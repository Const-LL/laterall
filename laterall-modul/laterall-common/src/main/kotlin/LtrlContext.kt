package ru.otus.otuskotlin.laterall.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs

data class LtrlContext(
    var command: LtrlCommand = LtrlCommand.NONE,
    var state: LtrlState = LtrlState.NONE,
    val errors: MutableList<LtrlError> = mutableListOf(),

    var workMode: LtrlWorkMode = LtrlWorkMode.PROD,
    var stubCase: LtrlStubs = LtrlStubs.NONE,

    var requestId: LtrlRequestId = LtrlRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var taskRequest: LtrlTask = LtrlTask(),
    var taskFilterRequest: LtrlTaskFilter = LtrlTaskFilter(),

    var taskResponse: LtrlTask = LtrlTask(),
    var tasksResponse: MutableList<LtrlTask> = mutableListOf(),

    )
