package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.biz.LtrlTaskProcessor
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.repo.common.TaskRepoInitialized
import ru.otus.otuskotlin.laterall.repo.inmemory.TaskRepoInMemory
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub

abstract class BaseBizValidationTest {
    protected abstract val command: LtrlCommand
    private val repo = TaskRepoInitialized(
        repo = TaskRepoInMemory(),
        initObjects = listOf(
            LtrlTaskStub.get(),
        ),
    )
    private val settings by lazy { LtrlCorSettings(repoTest = repo) }
    protected val processor by lazy { LtrlTaskProcessor(settings) }
}
