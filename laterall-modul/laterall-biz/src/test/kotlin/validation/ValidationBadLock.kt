package validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationLockCorrect(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTaskStub.get(),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LtrlState.FAILING, ctx.state)
}

fun validationLockTrim(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTaskStub.prepareResult {
            lock = LtrlTaskLock(" \n\t 123-234-abc-ABC \n\t ")
        },
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LtrlState.FAILING, ctx.state)
}

fun validationLockEmpty(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTaskStub.prepareResult {
            lock = LtrlTaskLock("")
        },
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LtrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationLockFormat(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTaskStub.prepareResult {
            lock = LtrlTaskLock("!@#\$%^&*(),.{}")
        },
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LtrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}
