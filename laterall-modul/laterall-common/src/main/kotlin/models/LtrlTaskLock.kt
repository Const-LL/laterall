package ru.otus.otuskotlin.laterall.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class LtrlTaskLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = LtrlTaskLock("")
    }
}
