package ru.otus.otuskotlin.laterall.repo.postgresql

import kotlinx.datetime.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.laterall.common.*
import ru.otus.otuskotlin.laterall.common.getTestInstant
import ru.otus.otuskotlin.laterall.common.models.*

class TaskTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val title = text(SqlFields.TITLE).nullable()
    val description = text(SqlFields.DESCRIPTION).nullable()
    val owner = text(SqlFields.OWNER_ID)
    val visibility = visibilityEnumeration(SqlFields.VISIBILITY)
    val lock = text(SqlFields.LOCK)
    val productId = text(SqlFields.PRODUCT_ID).nullable()

    val importance = importanceEnumeration(SqlFields.IMPORTANCE)

    val priority = integer(SqlFields.PRIORITY)

    var taskstart = text(SqlFields.TASKSTART)
    var taskend = text(SqlFields.TASKEND)
    var taskappend = text(SqlFields.TASKAPPEND)

    val group = groupEnumeration(SqlFields.GROUP)

    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = LtrlTask(
        id = LtrlTaskId(res[id].toString()),
        title = res[title] ?: "",
        description = res[description] ?: "",
        ownerId = LtrlUserId(res[owner].toString()),
        visibility = res[visibility],
        lock = LtrlTaskLock(res[lock]),
        productId = res[productId]?.let { LtrlProductId(it) } ?: LtrlProductId.NONE,
        importance = res[importance],
        priority = res[priority] ?: 0,
        group = res[group],
        taskstart = getInstant(res[taskstart]),
        taskend = getInstant(res[taskend]),
        taskappend = getInstant(res[taskappend])
    )

    fun to(it: UpdateBuilder<*>, task: LtrlTask, randomUuid: () -> String) {
        it[id] = task.id.takeIf { it != LtrlTaskId.NONE }?.asString() ?: randomUuid()
        it[title] = task.title
        it[description] = task.description
        it[owner] = task.ownerId.asString()
        it[visibility] = task.visibility
        it[lock] = task.lock.takeIf { it != LtrlTaskLock.NONE }?.asString() ?: randomUuid()
        it[productId] = task.productId.takeIf { it != LtrlProductId.NONE }?.asString()

        it[importance] = task.importance
        it[priority] = task.priority
        it[group] = task.group
        it[taskstart] = task.taskstart.toString()
        it[taskend] = task.taskend.toString()
        it[taskappend] = task.taskappend.toString()
    }

}

