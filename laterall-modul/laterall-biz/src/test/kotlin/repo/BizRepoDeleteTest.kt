package ru.otus.otuskotlin.laterall.biz.repo

import kotlinx.coroutines.test.runTest
import repo.repoNotFoundTest
import ru.otus.otuskotlin.laterall.repo.tests.TaskRepositoryMock
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseErr
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseOk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BizRepoDeleteTest {

    private val userId = LtrlUserId("321")
    private val command = LtrlCommand.DELETE
    private val initTask = LtrlTask(
        id = LtrlTaskId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        adType = LtrlDealSide.DEMAND,
        visibility = LtrlVisibility.VISIBLE_PUBLIC,
    )
    private val repo = TaskRepositoryMock(
        invokeReadTask = {
            DbTaskResponseOk(
                data = initTask,
            )
        },
        invokeDeleteTask = {
            if (it.id == initTask.id)
                DbTaskResponseOk(
                    data = initTask
                )
            else DbTaskResponseErr()
        }
    )
    private val settings by lazy {
        LtrlCorSettings(
            repoTest = repo
        )
    }
    private val processor = LtrlTaskProcessor(settings)

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val adToUpdate = LtrlTask(
            id = LtrlTaskId("123"),
            lock = LtrlTaskLock("123"),
        )
        val ctx = LtrlContext(
            command = command,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.TEST,
            taskRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(LtrlState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initTask.id, ctx.taskResponse.id)
        assertEquals(initTask.title, ctx.taskResponse.title)
        assertEquals(initTask.description, ctx.taskResponse.description)
        assertEquals(initTask.adType, ctx.taskResponse.adType)
        assertEquals(initTask.visibility, ctx.taskResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
