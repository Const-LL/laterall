package ru.otus.otuskotlin.laterall.biz.stub

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskCreateStubTest {

    private val processor = LtrlTaskProcessor()
    val id = LtrlTaskId("test-task-id")
    val title = "title 666"
    val description = "desc 666"
    val visibility = LtrlVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = LtrlContext(
            command = LtrlCommand.CREATE,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.SUCCESS,
            taskRequest = LtrlTask(
                id = id,
                title = title,
                description = description,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(LtrlTaskStub.get().id, ctx.taskResponse.id)
        assertEquals(title, ctx.taskResponse.title)
        assertEquals(description, ctx.taskResponse.description)
        assertEquals(visibility, ctx.taskResponse.visibility)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.CREATE,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.BAD_TITLE,
            taskRequest = LtrlTask(
                id = id,
                title = "",
                description = description,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(LtrlTask(), ctx.taskResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.CREATE,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.BAD_DESCRIPTION,
            taskRequest = LtrlTask(
                id = id,
                title = title,
                description = "",
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(LtrlTask(), ctx.taskResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.CREATE,
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
            command = LtrlCommand.CREATE,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.BAD_ID,
            taskRequest = LtrlTask(
                id = id,
                title = title,
                description = description,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(LtrlTask(), ctx.taskResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
