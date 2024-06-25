package ru.otus.otuskotlin.laterall.common.repo

import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.models.LtrlError

sealed interface IDbTasksResponse: IDbResponse<List<LtrlTask>>

data class DbTasksResponseOk(
    val data: List<LtrlTask>
): IDbTasksResponse

@Suppress("unused")
data class DbTasksResponseErr(
    val errors: List<LtrlError> = emptyList()
): IDbTasksResponse {
    constructor(err: LtrlError): this(listOf(err))
}
