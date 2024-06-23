package ru.otus.otuskotlin.laterall.biz.repo

import kotlinx.coroutines.test.runTest
import repo.repoNotFoundTest
import ru.otus.otuskotlin.laterall.repo.tests.TaskRepositoryMock
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseOk
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoReadTest {

    private val userId = LtrlUserId("321")
    private val command = LtrlCommand.READ
    private val initTask = LtrlTask(
        id = LtrlTaskId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        visibility = LtrlVisibility.VISIBLE_PUBLIC,
    )
    private val repo = TaskRepositoryMock(
        invokeReadTask = {
            DbTaskResponseOk(
                data = initTask,
            )
        }
    )
    private val settings = LtrlCorSettings(repoTest = repo)
    private val processor = LtrlTaskProcessor(settings)

    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = LtrlContext(
            command = command,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.TEST,
            taskRequest = LtrlTask(
                id = LtrlTaskId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(LtrlState.FINISHING, ctx.state)
        assertEquals(initTask.id, ctx.taskResponse.id)
        assertEquals(initTask.title, ctx.taskResponse.title)
        assertEquals(initTask.description, ctx.taskResponse.description)
        assertEquals(initTask.visibility, ctx.taskResponse.visibility)
    }

    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
