package ru.otus.otuskotlin.laterall.biz.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIdCorrect(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTask(
            id = LtrlTaskId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            visibility = LtrlVisibility.VISIBLE_PUBLIC,
            lock = LtrlTaskLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LtrlState.FAILING, ctx.state)
}

fun validationIdTrim(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTask(
            id = LtrlTaskId(" \n\t 123-234-abc-ABC \n\t "),
            title = "abc",
            description = "abc",
            visibility = LtrlVisibility.VISIBLE_PUBLIC,
            lock = LtrlTaskLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LtrlState.FAILING, ctx.state)
}

fun validationIdEmpty(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTask(
            id = LtrlTaskId(""),
            title = "abc",
            description = "abc",
            visibility = LtrlVisibility.VISIBLE_PUBLIC,
            lock = LtrlTaskLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LtrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationIdFormat(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTask(
            id = LtrlTaskId("!@#\$%^&*(),.{}"),
            title = "abc",
            description = "abc",
            visibility = LtrlVisibility.VISIBLE_PUBLIC,
            lock = LtrlTaskLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LtrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
