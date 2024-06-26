package ru.otus.otuskotlin.laterall.repo.postgresql

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.otuskotlin.laterall.common.helpers.asLtrlError
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.*
import ru.otus.otuskotlin.laterall.repo.common.IRepoTaskInitializable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
class RepoTaskSql constructor(
    properties: SqlProperties,
    private val randomUuid: () -> String = { uuid4().toString() },
) : IRepoTask, IRepoTaskInitializable {
    private val taskTable = TaskTable("${properties.schema}.${properties.table}")

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    fun clear(): Unit = transaction(conn) {
        taskTable.deleteAll()
    }

    private fun saveObj(task: LtrlTask): LtrlTask = transaction(conn) {
        val res = taskTable
            .insert {
                to(it, task, randomUuid)
            }
            .resultedValues
            ?.map { taskTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private suspend inline fun <T> transactionWrapper(crossinline block: () -> T, crossinline handle: (Exception) -> T): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbTaskResponse): IDbTaskResponse =
        transactionWrapper(block) { DbTaskResponseErr(it.asLtrlError()) }

    override fun save(tasks: Collection<LtrlTask>): Collection<LtrlTask> = tasks.map { saveObj(it) }
    override suspend fun createTask(rq: DbTaskRequest): IDbTaskResponse = transactionWrapper {
        DbTaskResponseOk(saveObj(rq.task))
    }

    private fun read(id: LtrlTaskId): IDbTaskResponse {
        val res = taskTable.selectAll().where {
            taskTable.id eq id.asString()
        }.singleOrNull() ?: return errorNotFound(id)
        return DbTaskResponseOk(taskTable.from(res))
    }

    override suspend fun readTask(rq: DbTaskIdRequest): IDbTaskResponse = transactionWrapper { read(rq.id) }

    private suspend fun update(
        id: LtrlTaskId,
        lock: LtrlTaskLock,
        block: (LtrlTask) -> IDbTaskResponse
    ): IDbTaskResponse =
        transactionWrapper {
            if (id == LtrlTaskId.NONE) return@transactionWrapper errorEmptyId

            val current = taskTable.selectAll().where { taskTable.id eq id.asString() }
                .singleOrNull()
                ?.let { taskTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorRepoConcurrency(current, lock)
                else -> block(current)
            }
        }


    override suspend fun updateTask(rq: DbTaskRequest): IDbTaskResponse = update(rq.task.id, rq.task.lock) {
        taskTable.update({ taskTable.id eq rq.task.id.asString() }) {
            to(it, rq.task.copy(lock = LtrlTaskLock(randomUuid())), randomUuid)
        }
        read(rq.task.id)
    }

    override suspend fun deleteTask(rq: DbTaskIdRequest): IDbTaskResponse = update(rq.id, rq.lock) {
        taskTable.deleteWhere { id eq rq.id.asString() }
        DbTaskResponseOk(it)
    }

    override suspend fun searchTask(rq: DbTaskFilterRequest): IDbTasksResponse =
        transactionWrapper({
            val res = taskTable.selectAll().where {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != LtrlUserId.NONE) {
                        add(taskTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (taskTable.title like "%${rq.titleFilter}%")
                                    or (taskTable.description like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbTasksResponseOk(data = res.map { taskTable.from(it) })
        }, {
            DbTasksResponseErr(it.asLtrlError())
        })
}