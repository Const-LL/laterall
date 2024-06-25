package ru.otus.otuskotlin.laterall.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.otuskotlin.laterall.common.helpers.errorSystem
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.*
import ru.otus.otuskotlin.laterall.repo.common.IRepoTaskInitializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import ru.otus.otuskotlin.laterall.common.repo.exceptions.RepoEmptyLockException

class TaskRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : TaskRepoBase(), IRepoTask, IRepoTaskInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, TaskEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(tasks: Collection<LtrlTask>) = tasks.map { ad ->
        val entity = TaskEntity(ad)
        require(entity.id != null)
        cache.put(entity.id, entity)
        ad
    }

    override suspend fun createTask(rq: DbTaskRequest): IDbTaskResponse = tryTaskMethod {
        val key = randomUuid()
        val task = rq.task.copy(id = LtrlTaskId(key), lock = LtrlTaskLock(randomUuid()))
        val entity = TaskEntity(task)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbTaskResponseOk(task)
    }

    override suspend fun readTask(rq: DbTaskIdRequest): IDbTaskResponse = tryTaskMethod {
        val key = rq.id.takeIf { it != LtrlTaskId.NONE }?.asString() ?: return@tryTaskMethod errorEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbTaskResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateTask(rq: DbTaskRequest): IDbTaskResponse = tryTaskMethod {
        val rqTask = rq.task
        val id = rqTask.id.takeIf { it != LtrlTaskId.NONE } ?: return@tryTaskMethod errorEmptyId
        val key = id.asString()
        val oldLock = rqTask.lock.takeIf { it != LtrlTaskLock.NONE } ?: return@tryTaskMethod errorEmptyLock(id)

        mutex.withLock {
            val oldTask = cache.get(key)?.toInternal()
            when {
                oldTask == null -> errorNotFound(id)
                oldTask.lock == LtrlTaskLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldTask.lock != oldLock -> errorRepoConcurrency(oldTask, oldLock)
                else -> {
                    val newTask = rqTask.copy()
                    val entity = TaskEntity(newTask)
                    cache.put(key, entity)
                    DbTaskResponseOk(newTask)
                }
            }
        }
    }


    override suspend fun deleteTask(rq: DbTaskIdRequest): IDbTaskResponse = tryTaskMethod {
        val id = rq.id.takeIf { it != LtrlTaskId.NONE } ?: return@tryTaskMethod errorEmptyId
        val key = id.asString()
        val oldLock = rq.lock.takeIf { it != LtrlTaskLock.NONE } ?: return@tryTaskMethod errorEmptyLock(id)

        mutex.withLock {
            val oldTask = cache.get(key)?.toInternal()
            when {
                oldTask == null -> errorNotFound(id)
                oldTask.lock == LtrlTaskLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldTask.lock != oldLock -> errorRepoConcurrency(oldTask, oldLock)
                else -> {
                    cache.invalidate(key)
                    DbTaskResponseOk(oldTask)
                }
            }
        }
    }

    /**
     * Поиск задач по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchTask(rq: DbTaskFilterRequest): IDbTasksResponse = tryTasksMethod {
        val result: List<LtrlTask> = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != LtrlUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbTasksResponseOk(result)
    }
}
