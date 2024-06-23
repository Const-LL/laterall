package repo

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.repo.tests.TaskRepositoryMock
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseOk
import ru.otus.otuskotlin.laterall.common.repo.errorNotFound
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val initTask = LtrlTask(
    id = LtrlTaskId("123"),
    title = "abc",
    description = "abc",
    visibility = LtrlVisibility.VISIBLE_PUBLIC,
)
private val repo = TaskRepositoryMock(
        invokeReadTask = {
            if (it.id == initTask.id) {
                DbTaskResponseOk(
                    data = initTask,
                )
            } else errorNotFound(it.id)
        }
    )
private val settings = LtrlCorSettings(repoTest = repo)
private val processor = LtrlTaskProcessor(settings)

fun repoNotFoundTest(command: LtrlCommand) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTask(
            id = LtrlTaskId("12345"),
            title = "xyz",
            description = "xyz",
            visibility = LtrlVisibility.VISIBLE_TO_GROUP,
            lock = LtrlTaskLock("123"),
        ),
    )
    processor.exec(ctx)
    assertEquals(LtrlState.FAILING, ctx.state)
    assertEquals(LtrlTask(), ctx.taskResponse)
    assertEquals(1, ctx.errors.size)
    assertNotNull(ctx.errors.find { it.code == "repo-not-found" }, "Errors must contain not-found")
}
