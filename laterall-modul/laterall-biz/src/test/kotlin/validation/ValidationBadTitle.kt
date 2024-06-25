package ru.otus.otuskotlin.laterall.biz.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = LtrlTaskStub.get()

fun validationTitleCorrect(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTask(
            id = stub.id,
            title = "abc",
            description = "abc",
            
            visibility = LtrlVisibility.VISIBLE_PUBLIC,
            lock = LtrlTaskLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LtrlState.FAILING, ctx.state)
    assertEquals("abc", ctx.taskValidated.title)
}

fun validationTitleTrim(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTask(
            id = stub.id,
            title = " \n\t abc \t\n ",
            description = "abc",
            
            visibility = LtrlVisibility.VISIBLE_PUBLIC,
            lock = LtrlTaskLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(LtrlState.FAILING, ctx.state)
    assertEquals("abc", ctx.taskValidated.title)
}

fun validationTitleEmpty(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTask(
            id = stub.id,
            title = "",
            description = "abc",
            
            visibility = LtrlVisibility.VISIBLE_PUBLIC,
            lock = LtrlTaskLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LtrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

fun validationTitleSymbols(command: LtrlCommand, processor: LtrlTaskProcessor) = runTest {
    val ctx = LtrlContext(
        command = command,
        state = LtrlState.NONE,
        workMode = LtrlWorkMode.TEST,
        taskRequest = LtrlTask(
            id = LtrlTaskId("123"),
            title = "!@#$%^&*(),.{}",
            description = "abc",
            
            visibility = LtrlVisibility.VISIBLE_PUBLIC,
            lock = LtrlTaskLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(LtrlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}
