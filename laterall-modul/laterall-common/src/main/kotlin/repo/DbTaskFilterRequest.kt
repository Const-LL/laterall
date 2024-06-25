package ru.otus.otuskotlin.laterall.common.repo

import ru.otus.otuskotlin.laterall.common.models.LtrlUserId

data class DbTaskFilterRequest(
    val titleFilter: String = "",
    val ownerId: LtrlUserId = LtrlUserId.NONE
)
