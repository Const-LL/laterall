package ru.otus.otuskotlin.laterall.repo.postgresql

import ru.otus.otuskotlin.laterall.repo.tests.*
import ru.otus.otuskotlin.laterall.common.repo.IRepoTask
import ru.otus.otuskotlin.laterall.repo.common.TaskRepoInitialized
import ru.otus.otuskotlin.laterall.repo.common.IRepoTaskInitializable
import kotlin.test.AfterTest

private fun IRepoTask.clear() {
    val pgRepo = (this as TaskRepoInitialized).repo as  RepoTaskSql
    pgRepo.clear()
}

class  RepoTaskSQLCreateTest :  RepoTaskCreateTest() {
    override val repo: IRepoTaskInitializable = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { uuidNew.asString() },
    )
    @AfterTest
    fun tearDown() = repo.clear()
}

class  RepoTaskSQLReadTest :  RepoTaskReadTest() {
    override val repo: IRepoTask = SqlTestCompanion.repoUnderTestContainer(initObjects)
    @AfterTest
    fun tearDown() = repo.clear()
}

class  RepoTaskSQLUpdateTest :  RepoTaskUpdateTest() {
    override val repo: IRepoTask = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
    @AfterTest
    fun tearDown() {
        repo.clear()
    }
}

class  RepoTaskSQLDeleteTest :  RepoTaskDeleteTest() {
    override val repo: IRepoTask = SqlTestCompanion.repoUnderTestContainer(initObjects)
    @AfterTest
    fun tearDown() = repo.clear()
}

class  RepoTaskSQLSearchTest :  RepoTaskSearchTest() {
    override val repo: IRepoTask = SqlTestCompanion.repoUnderTestContainer(initObjects)
    @AfterTest
    fun tearDown() = repo.clear()
}
