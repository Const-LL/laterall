package ru.otus.otuskotlin.laterall.repo.postgresql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.otuskotlin.laterall.common.models.*

class TaskTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val title = text(SqlFields.TITLE).nullable()
    val description = text(SqlFields.DESCRIPTION).nullable()
    val owner = text(SqlFields.OWNER_ID)
    val visibility = visibilityEnumeration(SqlFields.VISIBILITY)
    val lock = text(SqlFields.LOCK)
    val productId = text(SqlFields.PRODUCT_ID).nullable()

    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = LtrlTask(
        id = LtrlTaskId(res[id].toString()),
        title = res[title] ?: "",
        description = res[description] ?: "",
        ownerId = LtrlUserId(res[owner].toString()),
        visibility = res[visibility],
        lock = LtrlTaskLock(res[lock]),
        productId = res[productId]?.let { LtrlProductId(it) } ?: LtrlProductId.NONE,
    )

    fun to(it: UpdateBuilder<*>, task: LtrlTask, randomUuid: () -> String) {
        it[id] = task.id.takeIf { it != LtrlTaskId.NONE }?.asString() ?: randomUuid()
        it[title] = task.title
        it[description] = task.description
        it[owner] = task.ownerId.asString()
        it[visibility] = task.visibility
        it[lock] = task.lock.takeIf { it != LtrlTaskLock.NONE }?.asString() ?: randomUuid()
        it[productId] = task.productId.takeIf { it != LtrlProductId.NONE }?.asString()
    }

}

