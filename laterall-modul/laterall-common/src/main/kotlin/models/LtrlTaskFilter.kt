package ru.otus.otuskotlin.laterall.common.models

data class LtrlTaskFilter (
    var searchString: String = "",
    var ownerId: LtrlUserId = LtrlUserId.NONE,
)