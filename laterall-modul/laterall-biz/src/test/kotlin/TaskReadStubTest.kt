package ru.otus.otuskotlin.laterall.biz.stub

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.Test
import kotlin.test.assertEquals

class AdReadStubTest {

    private val processor = LtrlTaskProcessor()
    val id = LtrlTaskId("123")

    @Test
    fun read() = runTest {

        val ctx = LtrlContext(
            command = LtrlCommand.READ,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.SUCCESS,
            taskRequest = LtrlTask(
                id = id,
            ),
        )
        processor.exec(ctx)
        with (LtrlTaskStub.get()) {
            assertEquals(id, ctx.taskResponse.id)
            assertEquals(title, ctx.taskResponse.title)
            assertEquals(description, ctx.taskResponse.description)
            assertEquals(visibility, ctx.taskResponse.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.READ,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.BAD_ID,
            taskRequest = LtrlTask(),
        )
        processor.exec(ctx)
        assertEquals(LtrlTask(), ctx.taskResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.READ,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.DB_ERROR,
            taskRequest = LtrlTask(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(LtrlTask(), ctx.taskResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.READ,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.BAD_TITLE,
            taskRequest = LtrlTask(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(LtrlTask(), ctx.taskResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
