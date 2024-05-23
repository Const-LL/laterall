package ru.otus.otuskotlin.laterall.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class LtrlTaskId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = LtrlTaskId("")
    }
}
