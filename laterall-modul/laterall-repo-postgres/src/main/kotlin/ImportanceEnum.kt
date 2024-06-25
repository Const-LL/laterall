package ru.otus.otuskotlin.laterall.repo.postgresql

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskImportance

fun Table.importanceEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.IMPORTANCE_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.IMPORTANCE_LOW -> LtrlTaskImportance.LOW
            SqlFields.IMPORTANCE_MEDIUM -> LtrlTaskImportance.MEDIUM
            SqlFields.IMPORTANCE_HIGH -> LtrlTaskImportance.HIGH
            else -> LtrlTaskImportance.NONE
        }
    },
    toDb = { value ->
        when (value) {
            LtrlTaskImportance.LOW -> PgImportanceLow
            LtrlTaskImportance.MEDIUM -> PgImportanceMedium
            LtrlTaskImportance.HIGH -> PgImportanceHigh
            LtrlTaskImportance.NONE -> throw Exception("Wrong value of Importance. NONE is unsupported")
        }
    }
)

sealed class PgImportanceValue(eValue: String) : PGobject() {
    init {
        type = SqlFields.IMPORTANCE_TYPE
        value = eValue
    }
}

object PgImportanceHigh: PgImportanceValue(SqlFields.IMPORTANCE_HIGH) {
    private fun readResolve(): Any = PgImportanceHigh
}

object PgImportanceLow: PgImportanceValue(SqlFields.IMPORTANCE_LOW) {
    private fun readResolve(): Any = PgImportanceLow
}

object PgImportanceMedium: PgImportanceValue(SqlFields.IMPORTANCE_MEDIUM) {
    private fun readResolve(): Any = PgImportanceMedium
}
