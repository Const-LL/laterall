package ru.otus.otuskotlin.laterall.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class LtrlProductId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = LtrlProductId("")
    }
}
