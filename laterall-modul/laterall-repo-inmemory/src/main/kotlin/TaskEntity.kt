package ru.otus.otuskotlin.laterall.repo.inmemory

import ru.otus.otuskotlin.laterall.common.models.*

data class TaskEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val adType: String? = null,
    val visibility: String? = null,
    val lock: String? = null,
) {
    constructor(model: LtrlTask): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        visibility = model.visibility.takeIf { it != LtrlVisibility.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = LtrlTask(
        id = id?.let { LtrlTaskId(it) }?: LtrlTaskId.NONE,
        title = title?: "",
        description = description?: "",
        ownerId = ownerId?.let { LtrlUserId(it) }?: LtrlUserId.NONE,
        visibility = visibility?.let { LtrlVisibility.valueOf(it) }?: LtrlVisibility.NONE,
        lock = lock?.let { LtrlTaskLock(it) } ?: LtrlTaskLock.NONE,
    )
}
