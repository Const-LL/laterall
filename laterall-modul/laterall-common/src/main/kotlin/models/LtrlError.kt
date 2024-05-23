package ru.otus.otuskotlin.laterall.common.models

data class LtrlError (
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
