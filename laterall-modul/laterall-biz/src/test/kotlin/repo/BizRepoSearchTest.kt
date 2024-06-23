package ru.otus.otuskotlin.laterall.biz.repo

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.repo.tests.TaskRepositoryMock
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.DbTasksResponseOk
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val userId = LtrlUserId("321")
    private val command = LtrlCommand.SEARCH
    private val initTask = LtrlTask(
        id = LtrlTaskId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        visibility = LtrlVisibility.VISIBLE_PUBLIC,
    )
    private val repo = TaskRepositoryMock(
        invokeSearchTask = {
            DbTasksResponseOk(
                data = listOf(initTask),
            )
        }
    )
    private val settings = LtrlCorSettings(repoTest = repo)
    private val processor = LtrlTaskProcessor(settings)

    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = LtrlContext(
            command = command,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.TEST,
            taskFilterRequest = LtrlTaskFilter(
                searchString = "abc",
            ),
        )
        processor.exec(ctx)
        assertEquals(LtrlState.FINISHING, ctx.state)
        assertEquals(1, ctx.tasksResponse.size)
    }
}
