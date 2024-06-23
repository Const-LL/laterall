package ru.otus.otuskotlin.laterall.repo.common

import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.repo.IRepoTask

interface IRepoTaskInitializable: IRepoTask {
    fun save(tasks: Collection<LtrlTask>): Collection<LtrlTask>
}
