package ru.otus.otuskotlin.laterall.repo.postgresql

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskGroup

fun Table.groupEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.GROUP_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.GROUP_OTHER -> LtrlTaskGroup.OTHER
            SqlFields.GROUP_WORK -> LtrlTaskGroup.WORK
            SqlFields.GROUP_HOME -> LtrlTaskGroup.HOME
            SqlFields.GROUP_CHILDREN -> LtrlTaskGroup.CHILDREN
            SqlFields.GROUP_CAR -> LtrlTaskGroup.CAR
            else -> LtrlTaskGroup.NONE
        }
    },
    toDb = { value ->
        when (value) {
            LtrlTaskGroup.OTHER -> PgGroupOther
            LtrlTaskGroup.WORK -> PgGroupWork
            LtrlTaskGroup.HOME -> PgGroupHome
            LtrlTaskGroup.CHILDREN -> PgGroupChildren
            LtrlTaskGroup.CAR -> PgGroupCar
            LtrlTaskGroup.NONE -> throw Exception("Wrong value of Group. NONE is unsupported")
        }
    }
)


sealed class PgGroupValue(eValue: String) : PGobject() {
    init {
        type = SqlFields.GROUP_TYPE
        value = eValue
    }
}

object PgGroupOther: PgGroupValue(SqlFields.GROUP_OTHER) {
    private fun readResolve(): Any = PgGroupOther
}

object PgGroupWork: PgGroupValue(SqlFields.GROUP_WORK) {
    private fun readResolve(): Any = PgGroupWork
}

object PgGroupHome: PgGroupValue(SqlFields.GROUP_HOME) {
    private fun readResolve(): Any = PgGroupHome
}

object PgGroupChildren: PgGroupValue(SqlFields.GROUP_CHILDREN) {
    private fun readResolve(): Any = PgGroupChildren
}

object PgGroupCar: PgGroupValue(SqlFields.GROUP_CAR) {
    private fun readResolve(): Any = PgGroupCar
}
