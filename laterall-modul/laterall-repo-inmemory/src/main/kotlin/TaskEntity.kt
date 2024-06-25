package ru.otus.otuskotlin.laterall.repo.inmemory

import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeComponents
import ru.otus.otuskotlin.laterall.common.*
import ru.otus.otuskotlin.laterall.common.models.*

data class TaskEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val visibility: String? = null,
    val importance: String? = null,
    val lock: String? = null,
    val group: String? = null,
    val priority: Int? = null,
    val taskstart: String? = null,
    val taskend: String? = null,
    val taskappend: String? = null,

) {
    constructor(model: LtrlTask): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        visibility = model.visibility.takeIf { it != LtrlVisibility.NONE }?.name,
        importance = model.importance.takeIf { it != LtrlTaskImportance.NONE }?.name,
        group = model.group.takeIf { it != LtrlTaskGroup.NONE }?.name,
        priority = model.priority.takeIf { it >= 0 },
        lock = model.lock.asString().takeIf { it.isNotBlank() },
        taskstart = model.taskstart.toString().takeIf { it.isNotBlank() },
        taskend = model.taskend.toString().takeIf { it.isNotBlank() },
        taskappend = model.taskappend.toString().takeIf { it.isNotBlank() },
    )

    fun toInternal() = LtrlTask(
        id = id?.let { LtrlTaskId(it) }?: LtrlTaskId.NONE,
        title = title?: "",
        description = description?: "",
        ownerId = ownerId?.let { LtrlUserId(it) }?: LtrlUserId.NONE,
        visibility = visibility?.let { LtrlVisibility.valueOf(it) }?: LtrlVisibility.NONE,
        importance = importance?.let { LtrlTaskImportance.valueOf(it) }?: LtrlTaskImportance.NONE,
        group =  group?.let { LtrlTaskGroup.valueOf(it) }?: LtrlTaskGroup.NONE,
        priority = priority?: 0,
        lock = lock?.let { LtrlTaskLock(it) } ?: LtrlTaskLock.NONE,
        taskstart = taskstart?.let { getInstant(it) }!!,
        taskend = taskend?.let { getInstant(it) }!!,
        taskappend = taskappend?.let { getInstant(it) }!!,
    )
}
