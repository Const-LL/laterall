package ru.otus.otuskotlin.laterall.common.models

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.laterall.common.NONE

data class LtrlTask(
    var id: LtrlTaskId = LtrlTaskId.NONE,
    var title: String = "",
    var description: String = "",
    var importance: LtrlTaskImportance = LtrlTaskImportance.LOW,
    var priority: Int = 50,
    var taskstart: Instant = Instant.NONE,
    var taskend: Instant = Instant.NONE,
    var taskappend: Instant = Instant.NONE,
    var group: LtrlTaskGroup = LtrlTaskGroup.OTHER,
    var ownerId: LtrlUserId = LtrlUserId.NONE,
    var visibility: LtrlVisibility = LtrlVisibility.NONE,
    var lock: LtrlTaskLock = LtrlTaskLock.NONE,
    val permissionsClient: MutableSet<LtrlTaskPermissionClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = LtrlTask()
    }

}