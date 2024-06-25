package ru.otus.otuskotlin.laterall.repo.postgresql

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.laterall.common.NONE
import ru.otus.otuskotlin.laterall.common.models.*

object SqlFields {
    const val ID = "id"
    const val TITLE = "title"
    const val DESCRIPTION = "description"
    const val VISIBILITY = "visibility"
    const val LOCK = "lock"
    const val LOCK_OLD = "lock_old"
    const val OWNER_ID = "owner_id"
    const val PRODUCT_ID = "product_id"

    const val PRIORITY = "priority"

    const val IMPORTANCE = "importance"

    const val GROUP = "group"

    const val TASKSTART = "taskstart"
    const val TASKEND = "taskend"
    const val TASKAPPEND = "taskappend"

    const val VISIBILITY_TYPE = "task_visibilities_type"
    const val VISIBILITY_PUBLIC = "public"
    const val VISIBILITY_OWNER = "owner"
    const val VISIBILITY_GROUP = "group"

    const val IMPORTANCE_TYPE = "task_importance_type"
    const val IMPORTANCE_LOW = "low"
    const val IMPORTANCE_MEDIUM = "medium"
    const val IMPORTANCE_HIGH = "high"

    const val GROUP_TYPE = "task_groups_type"
    const val GROUP_OTHER = "other"
    const val GROUP_WORK = "work"
    const val GROUP_HOME = "home"
    const val GROUP_CHILDREN = "children"
    const val GROUP_CAR = "car"

    const val FILTER_TITLE = TITLE
    const val FILTER_OWNER_ID = OWNER_ID

    const val DELETE_OK = "DELETE_OK"

    fun String.quoted() = "\"$this\""
    val allFields = listOf(
        ID, TITLE, DESCRIPTION, VISIBILITY, LOCK, OWNER_ID, PRODUCT_ID,PRIORITY,IMPORTANCE,GROUP,TASKSTART,TASKEND,TASKAPPEND
    )
}