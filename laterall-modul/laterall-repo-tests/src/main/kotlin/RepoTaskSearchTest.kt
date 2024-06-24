package ru.otus.otuskotlin.laterall.repo.tests

import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.models.LtrlUserId
import ru.otus.otuskotlin.laterall.common.repo.DbTaskFilterRequest
import ru.otus.otuskotlin.laterall.common.repo.DbTasksResponseOk
import ru.otus.otuskotlin.laterall.common.repo.IRepoTask
//import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoTaskSearchTest {
    abstract val repo: IRepoTask

    protected open val initializedObjects: List<LtrlTask> = initObjects

//    @Test //todo
    fun searchOwner() = runRepoTest {
        val result = repo.searchTask(DbTaskFilterRequest(ownerId = searchOwnerId))
        assertIs<DbTasksResponseOk>(result)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object: BaseInitTasks("search") {

        val searchOwnerId = LtrlUserId("owner-124")
        override val initObjects: List<LtrlTask> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad4", ownerId = searchOwnerId),
        )
    }
}
