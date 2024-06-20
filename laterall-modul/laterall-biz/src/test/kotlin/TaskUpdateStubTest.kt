package ru.otus.otuskotlin.laterall.biz.stub

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.NONE
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import kotlin.test.Test
import kotlin.test.assertEquals


class AdUpdateStubTest {

    private val processor = LtrlTaskProcessor()
    val id = LtrlTaskId("test-task-id")
    val title = "test title"
    val description = "test description"
    val visibility = LtrlVisibility.VISIBLE_PUBLIC
    var importance = LtrlTaskImportance.LOW
    var priority = 50
    var taskstart = Instant.NONE
    var taskend = Instant.NONE
    var taskappend = Instant.NONE
    var group: LtrlTaskGroup = LtrlTaskGroup.OTHER

    @Test
    fun create() = runTest {

        val ctx = LtrlContext(
            command = LtrlCommand.UPDATE,
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
        assertEquals(id, ctx.taskResponse.id)
        assertEquals(title, ctx.taskResponse.title)
        assertEquals(description, ctx.taskResponse.description)
        assertEquals(visibility, ctx.taskResponse.visibility)
        assertEquals(importance, ctx.taskResponse.importance)
        assertEquals(priority, ctx.taskResponse.priority)
        assertEquals(group, ctx.taskResponse.group)
        assertEquals(taskstart, ctx.taskResponse.taskstart)
        assertEquals(taskend, ctx.taskResponse.taskend)
        assertEquals(taskappend, ctx.taskResponse.taskappend)
    }

    @Test
    fun badId() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.UPDATE,
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
    fun badTitle() = runTest {
        val ctx = LtrlContext(
            command = LtrlCommand.UPDATE,
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
            command = LtrlCommand.UPDATE,
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
            command = LtrlCommand.UPDATE,
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
            command = LtrlCommand.UPDATE,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.STUB,
            stubCase = LtrlStubs.BAD_SEARCH_STRING,
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
