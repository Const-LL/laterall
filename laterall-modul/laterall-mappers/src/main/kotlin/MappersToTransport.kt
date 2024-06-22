package ru.otus.otuskotlin.laterall.mappers

import ru.otus.otuskotlin.laterall.api.models.*
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.exceptions.UnknownLtrlCommand
import ru.otus.otuskotlin.laterall.common.models.*

fun LtrlContext.toTransportTask(): IResponse = when (val cmd = command) {
    LtrlCommand.CREATE -> toTransportCreate()
    LtrlCommand.READ -> toTransportRead()
    LtrlCommand.UPDATE -> toTransportUpdate()
    LtrlCommand.DELETE -> toTransportDelete()
    LtrlCommand.SEARCH -> toTransportSearch()
    LtrlCommand.NONE -> throw UnknownLtrlCommand(cmd)
}

fun LtrlContext.toTransportCreate() = TaskCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    task = taskResponse.toTransportTask()
)

fun LtrlContext.toTransportRead() = TaskReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    task = taskResponse.toTransportTask()
)

fun LtrlContext.toTransportUpdate() = TaskUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    task = taskResponse.toTransportTask()
)

fun LtrlContext.toTransportDelete() = TaskDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    task = taskResponse.toTransportTask()
)

fun LtrlContext.toTransportSearch() = TaskSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    tasks = tasksResponse.toTransportTask()
)

fun List<LtrlTask>.toTransportTask(): List<TaskResponseObject>? = this
    .map { it.toTransportTask() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun LtrlTask.toTransportTask(): TaskResponseObject = TaskResponseObject(
    id = id.takeIf { it != LtrlTaskId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != LtrlUserId.NONE }?.asString(),
    visibility = visibility.toTransportTask(),
    group = group.toTransportTask(),
    importance = importance.toTransportTask(),
    permissions = permissionsClient.toTransportTask(),
    taskstart = taskstart.toString(),
    taskend = taskend.toString(),
    taskappend = taskappend.toString(),
)

private fun Set<LtrlTaskPermissionClient>.toTransportTask(): Set<TaskPermissions>? = this
    .map { it.toTransportTask() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun LtrlTaskPermissionClient.toTransportTask() = when (this) {
    LtrlTaskPermissionClient.READ -> TaskPermissions.READ
    LtrlTaskPermissionClient.UPDATE -> TaskPermissions.UPDATE
    LtrlTaskPermissionClient.MAKE_VISIBLE_OWNER -> TaskPermissions.MAKE_VISIBLE_OWN
    LtrlTaskPermissionClient.MAKE_VISIBLE_GROUP -> TaskPermissions.MAKE_VISIBLE_GROUP
    LtrlTaskPermissionClient.MAKE_VISIBLE_PUBLIC -> TaskPermissions.MAKE_VISIBLE_PUBLIC
    LtrlTaskPermissionClient.DELETE -> TaskPermissions.DELETE
}

private fun LtrlVisibility.toTransportTask(): TaskVisibility? = when (this) {
    LtrlVisibility.VISIBLE_PUBLIC -> TaskVisibility.PUBLIC
    LtrlVisibility.VISIBLE_TO_GROUP -> TaskVisibility.GROUP_ONLY
    LtrlVisibility.VISIBLE_TO_OWNER -> TaskVisibility.OWNER_ONLY
    LtrlVisibility.NONE -> null
}

private fun LtrlTaskGroup.toTransportTask(): TaskGroup? = when (this) {
    LtrlTaskGroup.OTHER -> TaskGroup.OTHER
    LtrlTaskGroup.WORK -> TaskGroup.WORK
    LtrlTaskGroup.HOME -> TaskGroup.HOME
    LtrlTaskGroup.CHILDREN -> TaskGroup.CHILDREN
    LtrlTaskGroup.CAR -> TaskGroup.CAR
    LtrlTaskGroup.NONE -> null
}

private fun LtrlTaskImportance.toTransportTask(): TaskImportance? = when (this) {
    LtrlTaskImportance.LOW -> TaskImportance.LOW
    LtrlTaskImportance.MEDIUM -> TaskImportance.MEDIUM
    LtrlTaskImportance.HIGH -> TaskImportance.HIGH
    LtrlTaskImportance.NONE -> null
}

private fun List<LtrlError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportTask() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun LtrlError.toTransportTask() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun LtrlState.toResult(): ResponseResult? = when (this) {
    LtrlState.RUNNING -> ResponseResult.SUCCESS
    LtrlState.FAILING -> ResponseResult.ERROR
    LtrlState.FINISHING -> ResponseResult.SUCCESS
    LtrlState.NONE -> null
}
