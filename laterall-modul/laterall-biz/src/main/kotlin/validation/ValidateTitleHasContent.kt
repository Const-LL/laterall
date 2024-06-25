package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.helpers.errorValidation
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.fail

fun ICorChainDsl<LtrlContext>.validateTitleHasContent(title: String) = worker {
    this.title = title
    this.description = """
        Проверяем, что у нас есть какие-то слова в заголовке.
        Отказываем в публикации заголовков, в которых только бессмысленные символы типа %^&^$^%#^))&^*&%^^&
    """.trimIndent()
    val regExp = Regex("\\p{L}")
    on { taskValidating.title.isNotEmpty() && ! taskValidating.title.contains(regExp) }
    handle {
        fail(
            errorValidation(
            field = "title",
            violationCode = "noContent",
            description = "field must contain letters"
        )
        )
    }
}
