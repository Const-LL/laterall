package ru.otus.otuskotlin.laterall.app.spring.repo

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.slot
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import ru.otus.otuskotlin.laterall.app.spring.config.TaskConfig
import ru.otus.otuskotlin.laterall.app.spring.controllers.TaskController
import ru.otus.otuskotlin.laterall.common.repo.DbTaskFilterRequest
import ru.otus.otuskotlin.laterall.common.repo.DbTaskIdRequest
import ru.otus.otuskotlin.laterall.common.repo.DbTaskRequest
import ru.otus.otuskotlin.laterall.common.repo.IRepoTask
import ru.otus.otuskotlin.laterall.repo.common.TaskRepoInitialized
import ru.otus.otuskotlin.laterall.repo.inmemory.TaskRepoInMemory
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.Test

// Temporary simple test with stubs
@WebFluxTest(TaskController::class, TaskConfig::class)
internal class TaskRepoInMemoryTest : TaskRepoBaseTest() {
    @Autowired
    override lateinit var webClient: WebTestClient

    @MockkBean
    @Qualifier("testRepo")
    lateinit var testTestRepo: IRepoTask

    @BeforeEach
    fun tearUp() {
        val slotTask = slot<DbTaskRequest>()
        val slotId = slot<DbTaskIdRequest>()
        val slotFl = slot<DbTaskFilterRequest>()
        val repo = TaskRepoInitialized(
            repo = TaskRepoInMemory(randomUuid = { uuidNew }),
            initObjects = LtrlTaskStub.prepareCarTaskList("xx"))
        coEvery { testTestRepo.createTask(capture(slotTask)) } coAnswers { repo.createTask(slotTask.captured) }
        coEvery { testTestRepo.readTask(capture(slotId)) } coAnswers { repo.readTask(slotId.captured) }
        coEvery { testTestRepo.updateTask(capture(slotTask)) } coAnswers { repo.updateTask(slotTask.captured) }
        coEvery { testTestRepo.deleteTask(capture(slotId)) } coAnswers { repo.deleteTask(slotId.captured) }
        coEvery { testTestRepo.searchTask(capture(slotFl)) } coAnswers { repo.searchTask(slotFl.captured) }
    }

    @Test
    override fun createTask() = super.createTask()

    @Test
    override fun readTask() = super.readTask()

    @Test
    override fun updateTask() = super.updateTask()

    @Test
    override fun deleteTask() = super.deleteTask()

    @Test
    override fun searchTask() = super.searchTask()

}
