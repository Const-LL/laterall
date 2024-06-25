package ru.otus.otuskotlin.laterall.common.repo.exceptions

import ru.otus.otuskotlin.laterall.common.models.LtrlTaskId

class RepoEmptyLockException(id: LtrlTaskId): RepoTaskException(
    id,
    "Lock is empty in DB"
)


