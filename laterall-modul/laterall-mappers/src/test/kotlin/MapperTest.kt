package ru.otus.otuskotlin.laterall.mappers

import org.junit.Test
import ru.otus.otuskotlin.laterall.api.models.*
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = TaskCreateRequest(
            debug = TaskDebug(
                mode = TaskRequestDebugMode.STUB,
                stub = TaskRequestDebugStubs.SUCCESS,
            ),
            task = TaskCreateObject(
                title = "title",
                description = "desc",
                visibility = TaskVisibility.PUBLIC,
                group = TaskGroup.OTHER,
                importance = TaskImportance.LOW
            ),
        )

        val context = LtrlContext()
        context.fromTransport(req)

        assertEquals(LtrlStubs.SUCCESS, context.stubCase)
        assertEquals(LtrlWorkMode.STUB, context.workMode)
        assertEquals("title", context.taskRequest.title)
        assertEquals(LtrlVisibility.VISIBLE_PUBLIC, context.taskRequest.visibility)
        assertEquals(LtrlTaskGroup.OTHER, context.taskRequest.group)
        assertEquals(LtrlTaskImportance.LOW, context.taskRequest.importance)
    }

    @Test
    fun toTransport() {
        val context = LtrlContext(
            requestId = LtrlRequestId("1234"),
            command = LtrlCommand.CREATE,
            taskResponse = LtrlTask(
                title = "title",
                description = "desc",
                visibility = LtrlVisibility.VISIBLE_PUBLIC,
                group = LtrlTaskGroup.CAR,
                importance = LtrlTaskImportance.HIGH,
            ),
            errors = mutableListOf(
                LtrlError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = LtrlState.RUNNING,
        )

        val req = context.toTransportTask() as TaskCreateResponse

        assertEquals("title", req.task?.title)
        assertEquals("desc", req.task?.description)
        assertEquals(TaskVisibility.PUBLIC, req.task?.visibility)
        assertEquals(TaskGroup.CAR, req.task?.group)
        assertEquals(TaskImportance.HIGH, req.task?.importance)


        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
