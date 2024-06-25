package ru.otus.otuskotlin.laterall.biz.repo

import ru.otus.otuskotlin.laterall.biz.exceptions.LtrlTaskDbNotConfiguredException
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.errorSystem
import ru.otus.otuskotlin.laterall.common.helpers.fail
import ru.otus.otuskotlin.laterall.common.models.LtrlWorkMode
import ru.otus.otuskotlin.laterall.common.repo.IRepoTask
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker

fun ICorChainDsl<LtrlContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        taskRepo = when {
            workMode == LtrlWorkMode.TEST -> corSettings.repoTest
            workMode == LtrlWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != LtrlWorkMode.STUB && taskRepo == IRepoTask.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = LtrlTaskDbNotConfiguredException(workMode)
            )
        )
    }
}
