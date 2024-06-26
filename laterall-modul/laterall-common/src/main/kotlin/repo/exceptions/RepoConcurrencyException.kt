package ru.otus.otuskotlin.laterall.common.repo.exceptions

import ru.otus.otuskotlin.laterall.common.models.LtrlTaskId
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskLock

class RepoConcurrencyException(id: LtrlTaskId, expectedLock: LtrlTaskLock, actualLock: LtrlTaskLock?): RepoTaskException(
    id,
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
