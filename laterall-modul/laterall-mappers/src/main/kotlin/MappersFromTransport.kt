package ru.otus.otuskotlin.laterall.mappers

import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeComponents
import ru.otus.otuskotlin.laterall.api.models.*
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import ru.otus.otuskotlin.laterall.common.*

import ru.otus.otuskotlin.laterall.common.models.LtrlWorkMode
import ru.otus.otuskotlin.laterall.mappers.exceptions.UnknownRequestClass

fun LtrlContext.fromTransport(request: IRequest) = when (request) {
    is TaskCreateRequest -> fromTransport(request)
    is TaskReadRequest -> fromTransport(request)
    is TaskUpdateRequest -> fromTransport(request)
    is TaskDeleteRequest -> fromTransport(request)
    is TaskSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toTaskId() = this?.let { LtrlTaskId(it) } ?: LtrlTaskId.NONE
private fun String?.toTaskWithId() = LtrlTask(id = this.toTaskId())
private fun String?.toTaskLock() = this?.let { LtrlTaskLock(it) } ?: LtrlTaskLock.NONE

private fun TaskDebug?.transportToWorkMode(): LtrlWorkMode = when (this?.mode) {
    TaskRequestDebugMode.PROD -> LtrlWorkMode.PROD
    TaskRequestDebugMode.TEST -> LtrlWorkMode.TEST
    TaskRequestDebugMode.STUB -> LtrlWorkMode.STUB
    null -> LtrlWorkMode.PROD
}

private fun TaskDebug?.transportToStubCase(): LtrlStubs = when (this?.stub) {
    TaskRequestDebugStubs.SUCCESS -> LtrlStubs.SUCCESS
    TaskRequestDebugStubs.NOT_FOUND -> LtrlStubs.NOT_FOUND
    TaskRequestDebugStubs.BAD_ID -> LtrlStubs.BAD_ID
    TaskRequestDebugStubs.BAD_TITLE -> LtrlStubs.BAD_TITLE
    TaskRequestDebugStubs.BAD_DESCRIPTION -> LtrlStubs.BAD_DESCRIPTION
    TaskRequestDebugStubs.BAD_VISIBILITY -> LtrlStubs.BAD_VISIBILITY
    TaskRequestDebugStubs.CANNOT_DELETE -> LtrlStubs.CANNOT_DELETE
    TaskRequestDebugStubs.BAD_SEARCH_STRING -> LtrlStubs.BAD_SEARCH_STRING
    null -> LtrlStubs.NONE
}

fun LtrlContext.fromTransport(request: TaskCreateRequest) {
    command = LtrlCommand.CREATE
    taskRequest = request.task?.toInternal() ?: LtrlTask()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun LtrlContext.fromTransport(request: TaskReadRequest) {
    command = LtrlCommand.READ
    taskRequest = request.task.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TaskReadObject?.toInternal(): LtrlTask = if (this != null) {
    LtrlTask(id = id.toTaskId())
} else {
    LtrlTask()
}


fun LtrlContext.fromTransport(request: TaskUpdateRequest) {
    command = LtrlCommand.UPDATE
    taskRequest = request.task?.toInternal() ?: LtrlTask()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun LtrlContext.fromTransport(request: TaskDeleteRequest) {
    command = LtrlCommand.DELETE
    taskRequest = request.task.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TaskDeleteObject?.toInternal(): LtrlTask = if (this != null) {
    LtrlTask(
        id = id.toTaskId(),
        lock = lock.toTaskLock(),
    )
} else {
    LtrlTask()
}

fun LtrlContext.fromTransport(request: TaskSearchRequest) {
    command = LtrlCommand.SEARCH
    taskFilterRequest = request.taskFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TaskSearchFilter?.toInternal(): LtrlTaskFilter = LtrlTaskFilter(
    searchString = this?.searchString ?: "",
    ownerId = this?.ownerId?.let { LtrlUserId(it) } ?: LtrlUserId.NONE,
)

private fun TaskCreateObject.toInternal(): LtrlTask = LtrlTask(
    title = this.title ?: "",
    description = this.description ?: "",
    visibility = this.visibility.fromTransport(),
    group = this.group.fromTransport(),
    importance = this.importance.fromTransport(),
    taskstart = this.taskstart?.let { getInstant(it) }!!,
    taskend = this.taskend?.let { getInstant(it) }!!,
    taskappend = this.taskappend?.let { getInstant(it) }!!,
)

private fun TaskUpdateObject.toInternal(): LtrlTask = LtrlTask(
    id = this.id.toTaskId(),
    title = this.title ?: "",
    description = this.description ?: "",
    visibility = this.visibility.fromTransport(),
    group = this.group.fromTransport(),
    importance = this.importance.fromTransport(),
    taskstart = this.taskstart?.let { getInstant(it) }!!,
    taskend = this.taskend?.let { getInstant(it) }!!,
    taskappend = this.taskappend?.let { getInstant(it) }!!,
    lock = lock.toTaskLock(),
)

private fun TaskVisibility?.fromTransport(): LtrlVisibility = when (this) {
    TaskVisibility.PUBLIC -> LtrlVisibility.VISIBLE_PUBLIC
    TaskVisibility.OWNER_ONLY -> LtrlVisibility.VISIBLE_TO_OWNER
    TaskVisibility.GROUP_ONLY -> LtrlVisibility.VISIBLE_TO_GROUP
    null -> LtrlVisibility.NONE
}

private fun TaskGroup?.fromTransport(): LtrlTaskGroup = when (this) {
    TaskGroup.OTHER -> LtrlTaskGroup.OTHER
    TaskGroup.WORK -> LtrlTaskGroup.WORK
    TaskGroup.HOME -> LtrlTaskGroup.HOME
    TaskGroup.CHILDREN -> LtrlTaskGroup.CHILDREN
    TaskGroup.CAR -> LtrlTaskGroup.CAR
    null -> LtrlTaskGroup.NONE
}

private fun TaskImportance?.fromTransport(): LtrlTaskImportance = when (this) {
    TaskImportance.LOW -> LtrlTaskImportance.LOW
    TaskImportance.HIGH -> LtrlTaskImportance.HIGH
    TaskImportance.MEDIUM -> LtrlTaskImportance.MEDIUM
    null -> LtrlTaskImportance.NONE
}