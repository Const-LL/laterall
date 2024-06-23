package ru.otus.otuskotlin.laterall.repo.tests

import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoTaskUpdateTest {
    abstract val repo: IRepoTask
    protected open val updateSucc = initObjects[0]
    protected val updateIdNotFound = LtrlTaskId("ad-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        LtrlTask(
            id = updateSucc.id,
            title = "update object",
            description = "update object description",
            ownerId = LtrlUserId("owner-123"),
            visibility = LtrlVisibility.VISIBLE_TO_GROUP,
        )
    }
    private val reqUpdateNotFound = LtrlTask(
        id = updateIdNotFound,
        title = "update object not found",
        description = "update object not found description",
        ownerId = LtrlUserId("owner-123"),
        visibility = LtrlVisibility.VISIBLE_TO_GROUP,
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateTask(DbTaskRequest(reqUpdateSucc))
        assertIs<DbTaskResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.title, result.data.title)
        assertEquals(reqUpdateSucc.description, result.data.description)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateTask(DbTaskRequest(reqUpdateNotFound))
        assertIs<DbTaskResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitTasks("update") {
        override val initObjects: List<LtrlTask> = listOf(
            createInitTestModel("update"),
        )
    }
}
