package ru.otus.otuskotlin.laterall.repo.tests

import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.DbTaskRequest
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseOk
import ru.otus.otuskotlin.laterall.repo.common.IRepoTaskInitializable
import kotlin.test.*


abstract class RepoTaskCreateTest {
    abstract val repo: IRepoTaskInitializable
    protected open val uuidNew = LtrlTaskId("10000000-0000-0000-0000-000000000001")
    protected open val lockNew: LtrlTaskLock = LtrlTaskLock("20000000-0000-0000-0000-000000000002")

    private val createObj = LtrlTask(
        title = "create object",
        description = "create object description",
        ownerId = LtrlUserId("owner-123"),
        visibility = LtrlVisibility.VISIBLE_TO_GROUP,
    )

//    @Test //todo
    fun createSuccess() = runRepoTest {
        val result = repo.createTask(DbTaskRequest(createObj))
        val expected = createObj
        assertIs<DbTaskResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.title, result.data.title)
        assertEquals(expected.description, result.data.description)
        assertNotEquals(LtrlTaskId.NONE, result.data.id)
    }

    companion object : BaseInitTasks("create") {
        override val initObjects: List<LtrlTask> = emptyList()
    }
}
