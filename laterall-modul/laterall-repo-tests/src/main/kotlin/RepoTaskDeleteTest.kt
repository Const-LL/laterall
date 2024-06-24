package ru.otus.otuskotlin.laterall.repo.tests

import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskId
import ru.otus.otuskotlin.laterall.common.repo.*
//import kotlin.test.Test //todo_test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoTaskDeleteTest {
    abstract val repo: IRepoTask
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = LtrlTaskId("ad-repo-delete-notFound")

//    @Test //todo_test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteTask(DbTaskIdRequest(deleteSucc.id, lock = lockOld))
        assertIs<DbTaskResponseOk>(result)
        assertEquals(deleteSucc.title, result.data.title)
        assertEquals(deleteSucc.description, result.data.description)
    }

//    @Test //todo_test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readTask(DbTaskIdRequest(notFoundId))

        assertIs<DbTaskResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

//    @Test //todo
    fun deleteConcurrency() = runRepoTest {
        val result = repo.deleteTask(DbTaskIdRequest(deleteConc.id, lock = lockBad))

        assertIs<DbTaskResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertNotNull(error)
    }

    companion object : BaseInitTasks("delete") {
        override val initObjects: List<LtrlTask> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }
}
