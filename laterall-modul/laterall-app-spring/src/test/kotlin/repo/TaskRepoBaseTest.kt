package ru.otus.otuskotlin.laterall.app.spring.repo

import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.laterall.api.models.*
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.mappers.*
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.Test

internal abstract class TaskRepoBaseTest {
    protected abstract var webClient: WebTestClient
    private val debug = TaskDebug(mode = TaskRequestDebugMode.TEST)
    protected val uuidNew = "10000000-0000-0000-0000-000000000001"

    @Test
    open fun createTask() = testRepoTask(
        "create",
        TaskCreateRequest(
            task = LtrlTaskStub.get().toTransportCreate(),
            debug = debug,
        ),
        prepareCtx(LtrlTaskStub.prepareResult {
            id = LtrlTaskId(uuidNew)
            ownerId = LtrlUserId.NONE
            lock = LtrlTaskLock.NONE
        })
            .toTransportCreate()
            .copy(responseType = "create")
    )

    @Test
    open fun readTask() = testRepoTask(
        "read",
        TaskReadRequest(
            task = LtrlTaskStub.get().toTransportRead(),
            debug = debug,
        ),
        prepareCtx(LtrlTaskStub.get())
            .toTransportRead()
            .copy(responseType = "read")
    )

    @Test
    open fun updateTask() = testRepoTask(
        "update",
        TaskUpdateRequest(
            task =  LtrlTaskStub.prepareResult { title = "add" }.toTransportUpdate(),
            debug = debug,
        ),
        prepareCtx(LtrlTaskStub.prepareResult { title = "add" })
            .toTransportUpdate().copy(responseType = "update")
    )

    @Test
    open fun deleteTask() = testRepoTask(
        "delete",
        TaskDeleteRequest(
            task = LtrlTaskStub.get().toTransportDelete(),
            debug = debug,
        ),
        prepareCtx(LtrlTaskStub.get())
            .toTransportDelete()
            .copy(responseType = "delete")
    )

    @Test
    open fun searchTask() = testRepoTask(
        "search",
        TaskSearchRequest(
            taskFilter = TaskSearchFilter(),
            debug = debug,
        ),
        LtrlContext(
            state = LtrlState.RUNNING,
            tasksResponse = LtrlTaskStub.prepareCarTaskList("xx")
                .onEach { it.permissionsClient.clear() }
                .sortedBy { it.id.asString() }
                .toMutableList()
        )
            .toTransportSearch().copy(responseType = "search")
    )

    private fun prepareCtx(ad: LtrlTask) = LtrlContext(
        state = LtrlState.RUNNING,
        taskResponse = ad.apply {
            // Пока не реализована эта функциональность
            permissionsClient.clear()
        },
    )

    private inline fun <reified Req : Any, reified Res : IResponse> testRepoTask(
        url: String,
        requestObj: Req,
        expectObj: Res,
    ) {
        webClient
            .post()
            .uri("/task/$url")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                val sortedResp: IResponse = when (it) {
                    is TaskSearchResponse -> it.copy(tasks = it.tasks?.sortedBy { it.id })
                    else -> it
                }
                assertThat(sortedResp).isEqualTo(expectObj)
            }
    }
}
