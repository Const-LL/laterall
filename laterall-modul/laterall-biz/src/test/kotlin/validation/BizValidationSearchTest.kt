package ru.otus.otuskotlin.laterall.biz.validation

import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskFilter
import ru.otus.otuskotlin.laterall.common.models.LtrlCommand
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.models.LtrlWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationSearchTest: BaseBizValidationTest() {
    override val command = LtrlCommand.SEARCH

    @Test
    fun correctEmpty() = runTest {
        val ctx = LtrlContext(
            command = command,
            state = LtrlState.NONE,
            workMode = LtrlWorkMode.TEST,
            taskFilterRequest = LtrlTaskFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(LtrlState.FAILING, ctx.state)
    }
}
