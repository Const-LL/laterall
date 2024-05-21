package ru.otus.otuskotlin.laterall.api

import ru.otus.otuskotlin.laterall.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = TaskCreateRequest(
        debug = TaskDebug(
            mode = TaskRequestDebugMode.STUB,
            stub = TaskRequestDebugStubs.BAD_TITLE
        ),
        task = TaskCreateObject(
            title = "task title",
            description = "task description",
            visibility = TaskVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"task title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(request)
        val obj = apiMapper.readValue(json, IRequest::class.java) as TaskCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"task": null}
        """.trimIndent()
        val obj = apiMapper.readValue(jsonString, TaskCreateRequest::class.java)

        assertEquals(null, obj.task)
    }
}
