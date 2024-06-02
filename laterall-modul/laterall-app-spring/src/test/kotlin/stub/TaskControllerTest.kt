package ru.otus.otuskotlin.laterall.app.spring.stub

import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.laterall.app.spring.config.TaskConfig
import ru.otus.otuskotlin.laterall.app.spring.controllers.TaskController
import ru.otus.otuskotlin.laterall.api.models.*
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.mappers.*
import kotlin.test.Test

// Temporary simple test with stubs
@WebFluxTest(TaskController::class, TaskConfig::class)
internal class TaskControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Suppress("unused")
    @MockBean
    private lateinit var processor: LtrlTaskProcessor

    @Test
    fun createTask() = testStubTask(
        "/task/create",
        TaskCreateRequest(),
        LtrlContext().toTransportCreate().copy(responseType = "create")
    )

    @Test
    fun readTask() = testStubTask(
        "/task/read",
        TaskReadRequest(),
        LtrlContext().toTransportRead().copy(responseType = "read")
    )

    @Test
    fun updateTask() = testStubTask(
        "/task/update",
        TaskUpdateRequest(),
        LtrlContext().toTransportUpdate().copy(responseType = "update")
    )

    @Test
    fun deleteTask() = testStubTask(
        "/task/delete",
        TaskDeleteRequest(),
        LtrlContext().toTransportDelete().copy(responseType = "delete")
    )

    @Test
    fun searchTask() = testStubTask(
        "/task/search",
        TaskSearchRequest(),
        LtrlContext().toTransportSearch().copy(responseType = "search")
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubTask(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                assertThat(it).isEqualTo(responseObj)
            }
    }
}
