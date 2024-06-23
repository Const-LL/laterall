package ru.otus.otuskotlin.laterall.biz.repo

import kotlinx.coroutines.test.runTest
import repo.repoNotFoundTest
import ru.otus.otuskotlin.laterall.repo.tests.TaskRepositoryMock
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseOk
import ru.otus.otuskotlin.laterall.common.repo.DbTasksResponseOk
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoOffersTest {

    private val userId = LtrlUserId("321")
    private val command = LtrlCommand.OFFERS
    private val initTask = LtrlTask(
        id = LtrlTaskId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        adType = LtrlDealSide.DEMAND,
        visibility = LtrlVisibility.VISIBLE_PUBLIC,
    )
    private val offerTask = LtrlTask(
        id = LtrlTaskId("321"),
        title = "abcd",
        description = "xyz",
        adType = LtrlDealSide.SUPPLY,
        visibility = LtrlVisibility.VISIBLE_PUBLIC,
    )
    private val repo = TaskRepositoryMock(
        invokeReadTask = {
            DbTaskResponseOk(
                data = initTask
            )
        },
        invokeSearchTask = {
            DbTasksResponseOk(
                data = listOf(offerTask)
            )
        }
    )
    private val settings = LtrlCorSettings(
        repoTest = repo
    )
    private val processor = LtrlTaskProcessor(settings)

    @Test
    fun repoOffersSuccessTest() = runTest {
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
        assertEquals(1, ctx.tasksResponse.size)
        assertEquals(LtrlDealSide.SUPPLY, ctx.tasksResponse.first().adType)
    }

    @Test
    fun repoOffersNotFoundTest() = repoNotFoundTest(command)
}
