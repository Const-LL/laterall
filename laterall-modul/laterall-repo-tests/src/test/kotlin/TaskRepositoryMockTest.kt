package ru.otus.otuskotlin.laterall.repo.tests

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.repo.*
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TaskRepositoryMockTest {
    private val repo = TaskRepositoryMock(
        invokeCreateTask = { DbTaskResponseOk(LtrlTaskStub.prepareResult { title = "create" }) },
        invokeReadTask = { DbTaskResponseOk(LtrlTaskStub.prepareResult { title = "read" }) },
        invokeUpdateTask = { DbTaskResponseOk(LtrlTaskStub.prepareResult { title = "update" }) },
        invokeDeleteTask = { DbTaskResponseOk(LtrlTaskStub.prepareResult { title = "delete" }) },
        invokeSearchTask = { DbTasksResponseOk(listOf(LtrlTaskStub.prepareResult { title = "search" })) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createTask(DbTaskRequest(LtrlTask()))
        assertIs<DbTaskResponseOk>(result)
        assertEquals("create", result.data.title)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readTask(DbTaskIdRequest(LtrlTask()))
        assertIs<DbTaskResponseOk>(result)
        assertEquals("read", result.data.title)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateTask(DbTaskRequest(LtrlTask()))
        assertIs<DbTaskResponseOk>(result)
        assertEquals("update", result.data.title)
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteTask(DbTaskIdRequest(LtrlTask()))
        assertIs<DbTaskResponseOk>(result)
        assertEquals("delete", result.data.title)
    }

    @Test
    fun mockSearch() = runTest {
        val result = repo.searchTask(DbTaskFilterRequest())
        assertIs<DbTasksResponseOk>(result)
        assertEquals("search", result.data.first().title)
    }

}
