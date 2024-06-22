package ru.otus.otuskotlin.laterall.mappers

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.junit.Test
import ru.otus.otuskotlin.laterall.api.models.*
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import kotlin.test.assertEquals


class MapperTest {

    private fun getTestInstant(timeString: String) : Instant
    {
        val ldt: LocalDateTime = LocalDateTime.parse(timeString)
        return ldt.toInstant(TimeZone.UTC)
    }

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
                importance = TaskImportance.LOW,
                taskstart = "2024-05-20T08:09:10Z",
                taskend = "2024-05-20T08:09:10Z",
                taskappend = "2024-05-20T08:09:10Z"
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
        assertEquals(getTestInstant("2024-05-20T08:09:10"), context.taskRequest.taskstart)
        assertEquals(getTestInstant("2024-05-20T08:09:10"), context.taskRequest.taskend)
        assertEquals(getTestInstant("2024-05-20T08:09:10"), context.taskRequest.taskappend)
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
                taskstart = getTestInstant("2024-05-20T08:09:10"),
                taskend = getTestInstant("2024-05-20T08:09:10"),
                taskappend = getTestInstant("2024-05-20T08:09:10")
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
        assertEquals("2024-05-20T08:09:10Z", req.task?.taskstart)
        assertEquals("2024-05-20T08:09:10Z", req.task?.taskend)
        assertEquals("2024-05-20T08:09:10Z", req.task?.taskappend)


        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
