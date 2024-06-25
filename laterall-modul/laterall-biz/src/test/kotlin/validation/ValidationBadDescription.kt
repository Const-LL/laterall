package ru.otus.otuskotlin.laterall.biz.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationDescriptionCorrect(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTaskStub.get(),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LtrlState.FAILING, ctx.state)
    assertContains(ctx.taskValidated.description, "abc")
}

fun validationDescriptionTrim(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTaskStub.prepareResult {
            description = " \n\tabc \n\t"
        },
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LtrlState.FAILING, ctx.state)
    assertEquals("abc", ctx.taskValidated.description)
}

fun validationDescriptionEmpty(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTaskStub.prepareResult {
            description = ""
        },
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LtrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

fun validationDescriptionSymbols(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTaskStub.prepareResult {
            description = "!@#$%^&*(),.{}"
        },
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LtrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
