package ru.otus.otuskotlin.laterall.mappers

import ru.otus.otuskotlin.laterall.api.models.*
import ru.otus.otuskotlin.laterall.common.models.*

fun LtrlTask.toTransportCreate() = TaskCreateObject(
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    visibility = visibility.toTransportTask(),
)

fun LtrlTask.toTransportRead() = TaskReadObject(
    id = id.takeIf { it != LtrlTaskId.NONE }?.asString(),
)

fun LtrlTask.toTransportUpdate() = TaskUpdateObject(
    id = id.takeIf { it != LtrlTaskId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    visibility = visibility.toTransportTask(),
    lock = lock.takeIf { it != LtrlTaskLock.NONE }?.asString(),
)

fun LtrlTask.toTransportDelete() = TaskDeleteObject(
    id = id.takeIf { it != LtrlTaskId.NONE }?.asString(),
    lock = lock.takeIf { it != LtrlTaskLock.NONE }?.asString(),
)
