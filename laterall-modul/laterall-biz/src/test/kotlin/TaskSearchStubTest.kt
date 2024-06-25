package ru.otus.otuskotlin.laterall.biz.stub

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class TaskSearchStubTest {

    private val processor = LtrlTaskProcessor()
    val filter = LtrlTaskFilter(searchString = "bolt")

    @Test
    fun read() = runTest {

        val ctx = LtrlContext(
            command = LtrlCommand.SEARCH,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.SUCCESS,
            taskFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.tasksResponse.size > 1)
        val first = ctx.tasksResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (LtrlTaskStub.get()) {
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.SEARCH,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.BAD_ID,
            taskFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(LtrlTask(), ctx.taskResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.SEARCH,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.DB_ERROR,
            taskFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(LtrlTask(), ctx.taskResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.SEARCH,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.BAD_TITLE,
            taskFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(LtrlTask(), ctx.taskResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
