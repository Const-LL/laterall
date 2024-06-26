package ru.otus.otuskotlin.laterall.repo.common

import ru.otus.otuskotlin.laterall.common.models.LtrlTask

/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class TaskRepoInitialized(
    val repo: IRepoTaskInitializable,
    initObjects: Collection<LtrlTask> = emptyList(),
) : IRepoTaskInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<LtrlTask> = save(initObjects).toList()
}
