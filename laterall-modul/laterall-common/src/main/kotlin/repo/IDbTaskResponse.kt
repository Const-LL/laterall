package ru.otus.otuskotlin.laterall.common.repo

import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.models.LtrlError

sealed interface IDbTaskResponse: IDbResponse<LtrlTask>

data class DbTaskResponseOk(
    val data: LtrlTask
): IDbTaskResponse

data class DbTaskResponseErr(
    val errors: List<LtrlError> = emptyList()
): IDbTaskResponse {
    constructor(err: LtrlError): this(listOf(err))
}

data class DbTaskResponseErrWithData(
    val data: LtrlTask,
    val errors: List<LtrlError> = emptyList()
): IDbTaskResponse {
    constructor(ad: LtrlTask, err: LtrlError): this(ad, listOf(err))
}
