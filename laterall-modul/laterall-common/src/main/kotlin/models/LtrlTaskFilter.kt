package ru.otus.otuskotlin.laterall.common.models

data class LtrlTaskFilter (
    var searchString: String = "",
    var ownerId: LtrlUserId = LtrlUserId.NONE,
){
    fun deepCopy(): LtrlTaskFilter = copy()

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = LtrlTaskFilter()
    }
}