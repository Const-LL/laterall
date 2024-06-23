package ru.otus.otuskotlin.laterall.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.IRepoTask
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs

data class LtrlContext(
    var command: LtrlCommand = LtrlCommand.NONE,
    var state: LtrlState = LtrlState.NONE,
    val errors: MutableList<LtrlError> = mutableListOf(),

    var corSettings: LtrlCorSettings = LtrlCorSettings(),
    var workMode: LtrlWorkMode = LtrlWorkMode.PROD,
    var stubCase: LtrlStubs = LtrlStubs.NONE,

    var requestId: LtrlRequestId = LtrlRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var taskRequest: LtrlTask = LtrlTask(),
    var taskFilterRequest: LtrlTaskFilter = LtrlTaskFilter(),

    var taskValidating: LtrlTask = LtrlTask(),
    var taskFilterValidating: LtrlTaskFilter = LtrlTaskFilter(),

    var taskValidated: LtrlTask = LtrlTask(),
    var taskFilterValidated: LtrlTaskFilter = LtrlTaskFilter(),

    var taskRepo: IRepoTask = IRepoTask.NONE,
    var taskRepoRead: LtrlTask = LtrlTask(), // ��, ��� ��������� �� �����������
    var taskRepoPrepare: LtrlTask = LtrlTask(), // ��, ��� ������� ��� ���������� � ��
    var taskRepoDone: LtrlTask = LtrlTask(),  // ���������, ���������� �� ��
    var tasksRepoDone: MutableList<LtrlTask> = mutableListOf(),
    var taskResponse: LtrlTask = LtrlTask(),
    var tasksResponse: MutableList<LtrlTask> = mutableListOf(),
)
