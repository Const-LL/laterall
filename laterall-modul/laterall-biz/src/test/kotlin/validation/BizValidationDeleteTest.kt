package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.common.models.LtrlCommand
import validation.validationLockCorrect
import validation.validationLockEmpty
import validation.validationLockFormat
import validation.validationLockTrim
import kotlin.test.Test

class BizValidationDeleteTest: BaseBizValidationTest() {
    override val command = LtrlCommand.DELETE

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)

}
