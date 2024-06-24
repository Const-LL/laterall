package ru.otus.otuskotlin.laterall.repo.postgresql

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import ru.otus.otuskotlin.laterall.common.models.LtrlVisibility

fun Table.visibilityEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.VISIBILITY_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.VISIBILITY_OWNER -> LtrlVisibility.VISIBLE_TO_OWNER
            SqlFields.VISIBILITY_GROUP -> LtrlVisibility.VISIBLE_TO_GROUP
            SqlFields.VISIBILITY_PUBLIC -> LtrlVisibility.VISIBLE_PUBLIC
            else -> LtrlVisibility.NONE
        }
    },
    toDb = { value ->
        when (value) {
//            LtrlVisibility.VISIBLE_TO_OWNER -> PGobject().apply { type = SqlFields.VISIBILITY_TYPE; value = SqlFields.VISIBILITY_OWNER}
            LtrlVisibility.VISIBLE_TO_OWNER -> PgVisibilityOwner
            LtrlVisibility.VISIBLE_TO_GROUP -> PgVisibilityGroup
            LtrlVisibility.VISIBLE_PUBLIC -> PgVisibilityPublic
            LtrlVisibility.NONE -> throw Exception("Wrong value of Visibility. NONE is unsupported")
        }
    }
)

sealed class PgVisibilityValue(eValue: String) : PGobject() {
    init {
        type = SqlFields.VISIBILITY_TYPE
        value = eValue
    }
}

object PgVisibilityPublic: PgVisibilityValue(SqlFields.VISIBILITY_PUBLIC) {
    private fun readResolve(): Any = PgVisibilityPublic
}

object PgVisibilityOwner: PgVisibilityValue(SqlFields.VISIBILITY_OWNER) {
    private fun readResolve(): Any = PgVisibilityOwner
}

object PgVisibilityGroup: PgVisibilityValue(SqlFields.VISIBILITY_GROUP) {
    private fun readResolve(): Any = PgVisibilityGroup
}
