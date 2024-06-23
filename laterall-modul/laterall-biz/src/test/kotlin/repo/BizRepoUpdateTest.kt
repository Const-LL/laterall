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

class BizRepoUpdateTest {

    private val userId = LtrlUserId("321")
    private val command = LtrlCommand.UPDATE
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
        invokeUpdateTask = {
            DbTaskResponseOk(
                data = LtrlTask(
                    id = LtrlTaskId("123"),
                    title = "xyz",
                    description = "xyz",
                    adType = LtrlDealSide.DEMAND,
                    visibility = LtrlVisibility.VISIBLE_TO_GROUP,
                )
            )
        }
    )
    private val settings = LtrlCorSettings(repoTest = repo)
    private val processor = LtrlTaskProcessor(settings)

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = LtrlTask(
            id = LtrlTaskId("123"),
            title = "xyz",
            description = "xyz",
            adType = LtrlDealSide.DEMAND,
            visibility = LtrlVisibility.VISIBLE_TO_GROUP,
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
        assertEquals(adToUpdate.id, ctx.taskResponse.id)
        assertEquals(adToUpdate.title, ctx.taskResponse.title)
        assertEquals(adToUpdate.description, ctx.taskResponse.description)
        assertEquals(adToUpdate.adType, ctx.taskResponse.adType)
        assertEquals(adToUpdate.visibility, ctx.taskResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
