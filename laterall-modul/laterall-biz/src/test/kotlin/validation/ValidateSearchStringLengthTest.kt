package ru.otus.otuskotlin.laterall.biz.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskFilter
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.cor.rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateSearchStringLengthTest {
    @Test
    fun emptyString() = runTest {
        val ctx = LtrlContext(state = LtrlState.RUNNING, taskFilterValidating = LtrlTaskFilter(searchString = ""))
        chain.exec(ctx)
        assertEquals(LtrlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun blankString() = runTest {
        val ctx = LtrlContext(state = LtrlState.RUNNING, taskFilterValidating = LtrlTaskFilter(searchString = "  "))
        chain.exec(ctx)
        assertEquals(LtrlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun shortString() = runTest {
        val ctx = LtrlContext(state = LtrlState.RUNNING, taskFilterValidating = LtrlTaskFilter(searchString = "12"))
        chain.exec(ctx)
        assertEquals(LtrlState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooShort", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = LtrlContext(state = LtrlState.RUNNING, taskFilterValidating = LtrlTaskFilter(searchString = "123"))
        chain.exec(ctx)
        assertEquals(LtrlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun longString() = runTest {
        val ctx = LtrlContext(state = LtrlState.RUNNING, taskFilterValidating = LtrlTaskFilter(searchString = "12".repeat(51)))
        chain.exec(ctx)
        assertEquals(LtrlState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooLong", ctx.errors.first().code)
    }

    companion object {
        val chain = rootChain {
            validateSearchStringLength("")
        }.build()
    }
}
