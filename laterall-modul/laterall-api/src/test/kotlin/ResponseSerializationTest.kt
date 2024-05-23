package ru.otus.otuskotlin.laterall.api

import ru.otus.otuskotlin.laterall.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = TaskCreateResponse(
        task = TaskResponseObject(
            title = "task title",
            description = "task description",
            visibility = TaskVisibility.PUBLIC,
            importance = TaskImportance.LOW,
            group = TaskGroup.OTHER
            //todo all time parameters
        )
    )

    @Test
    fun serialize() {
        val json = apiMapper.writeValueAsString(response)

        assertContains(json, Regex("\"title\":\\s*\"task title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
        assertContains(json, Regex("\"importance\":\\s*\"low\""))
        assertContains(json, Regex("\"group\":\\s*\"other\""))
    }

    @Test
    fun deserialize() {
        val json = apiMapper.writeValueAsString(response)
        val obj = apiMapper.readValue(json, IResponse::class.java) as TaskCreateResponse

        assertEquals(response, obj)
    }
}
