package ru.otus.otuskotlin.laterall.biz.repo

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.repo.tests.TaskRepositoryMock
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseOk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = LtrlUserId("321")
    private val command = LtrlCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = TaskRepositoryMock(
        invokeCreateTask = {
            DbTaskResponseOk(
                data = LtrlTask(
                    id = LtrlTaskId(uuid),
                    title = it.task.title,
                    description = it.task.description,
                    ownerId = userId,
                    visibility = it.task.visibility,
                )
            )
        }
    )
    private val settings = LtrlCorSettings(
        repoTest = repo
    )
    private val processor = LtrlTaskProcessor(settings)

    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = LtrlContext(
            command = command,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.TEST,
            taskRequest = LtrlTask(
                title = "abc",
                description = "abc",
                visibility = LtrlVisibility.VISIBLE_PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(LtrlState.FINISHING, ctx.state)
        assertNotEquals(LtrlTaskId.NONE, ctx.taskResponse.id)
        assertEquals("abc", ctx.taskResponse.title)
        assertEquals("abc", ctx.taskResponse.description)
        assertEquals(LtrlVisibility.VISIBLE_PUBLIC, ctx.taskResponse.visibility)
    }
}
