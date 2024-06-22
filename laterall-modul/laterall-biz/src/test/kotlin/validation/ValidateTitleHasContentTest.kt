package ru.otus.otuskotlin.laterall.biz.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskFilter
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.cor.rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateTitleHasContentTest {
    @Test
    fun emptyString() = runTest {
        val ctx = LtrlContext(state = LtrlState.RUNNING, taskValidating = LtrlTask(title = ""))
        chain.exec(ctx)
        assertEquals(LtrlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun noContent() = runTest {
        val ctx = LtrlContext(state = LtrlState.RUNNING, taskValidating = LtrlTask(title = "12!@#$%^&*()_+-="))
        chain.exec(ctx)
        assertEquals(LtrlState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-title-noContent", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = LtrlContext(state = LtrlState.RUNNING, taskFilterValidating = LtrlTaskFilter(searchString = "Ж"))
        chain.exec(ctx)
        assertEquals(LtrlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    companion object {
        val chain = rootChain {
            validateTitleHasContent("")
        }.build()
    }
}
