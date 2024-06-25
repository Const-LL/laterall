package ru.otus.otuskotlin.laterall.repo.postgresql

import com.benasher44.uuid.uuid4
import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.repo.common.TaskRepoInitialized
import ru.otus.otuskotlin.laterall.repo.common.IRepoTaskInitializable

object SqlTestCompanion {
    private const val HOST = "localhost"
    private const val USER = "postgres"
    private const val PASS = "postgres"
    val PORT = getEnv("postgresPort")?.toIntOrNull() ?: 5432

    fun repoUnderTestContainer(
        initObjects: Collection<LtrlTask> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): IRepoTaskInitializable = TaskRepoInitialized(
        repo =  RepoTaskSql(
            SqlProperties(
                host = HOST,
                user = USER,
                password = PASS,
                port = PORT,
            ),
            randomUuid = randomUuid
        ),
        initObjects = initObjects,
    )
}

