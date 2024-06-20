package  ru.otus.otuskotlin.laterall.biz

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.biz.general.initStatus
import ru.otus.otuskotlin.laterall.biz.general.operation
import ru.otus.otuskotlin.laterall.biz.general.stubs
import ru.otus.otuskotlin.laterall.biz.stubs.*
import ru.otus.otuskotlin.laterall.cor.rootChain
import ru.otus.otuskotlin.laterall.common.models.LtrlCommand

@Suppress("unused", "RedundantSuspendModifier")
class LtrlTaskProcessor(private val corSettings: LtrlCorSettings = LtrlCorSettings.NONE) {
    suspend fun exec(ctx: LtrlContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<LtrlContext> {
        initStatus("Инициализация статуса")
        operation("Создание задачи", LtrlCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
//                stubValidationBadTitle("Имитация ошибки валидации заголовка")
//                stubValidationBadDescription("Имитация ошибки валидации описания")
//                stubDbError("Имитация ошибки работы с БД")
//                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
    }.build()
}

//
//class MkplAdProcessor(
//    private val corSettings: MkplCorSettings = MkplCorSettings.NONE
//) {
//    suspend fun exec(ctx: MkplContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })
//
//    private val businessChain = rootChain<MkplContext> {
//        initStatus("Инициализация статуса")
//
//        operation("Создание объявления", MkplCommand.CREATE) {
//            stubs("Обработка стабов") {
//                stubCreateSuccess("Имитация успешной обработки", corSettings)
//                stubValidationBadTitle("Имитация ошибки валидации заголовка")
//                stubValidationBadDescription("Имитация ошибки валидации описания")
//                stubDbError("Имитация ошибки работы с БД")
//                stubNoCase("Ошибка: запрошенный стаб недопустим")
//            }
//        }
//        operation("Получить объявление", MkplCommand.READ) {
//            stubs("Обработка стабов") {
//                stubReadSuccess("Имитация успешной обработки", corSettings)
//                stubValidationBadId("Имитация ошибки валидации id")
//                stubDbError("Имитация ошибки работы с БД")
//                stubNoCase("Ошибка: запрошенный стаб недопустим")
//            }
//        }
//        operation("Изменить объявление", MkplCommand.UPDATE) {
//            stubs("Обработка стабов") {
//                stubUpdateSuccess("Имитация успешной обработки", corSettings)
//                stubValidationBadId("Имитация ошибки валидации id")
//                stubValidationBadTitle("Имитация ошибки валидации заголовка")
//                stubValidationBadDescription("Имитация ошибки валидации описания")
//                stubDbError("Имитация ошибки работы с БД")
//                stubNoCase("Ошибка: запрошенный стаб недопустим")
//            }
//        }
//        operation("Удалить объявление", MkplCommand.DELETE) {
//            stubs("Обработка стабов") {
//                stubDeleteSuccess("Имитация успешной обработки", corSettings)
//                stubValidationBadId("Имитация ошибки валидации id")
//                stubDbError("Имитация ошибки работы с БД")
//                stubNoCase("Ошибка: запрошенный стаб недопустим")
//            }
//        }
//        operation("Поиск объявлений", MkplCommand.SEARCH) {
//            stubs("Обработка стабов") {
//                stubSearchSuccess("Имитация успешной обработки", corSettings)
//                stubValidationBadId("Имитация ошибки валидации id")
//                stubDbError("Имитация ошибки работы с БД")
//                stubNoCase("Ошибка: запрошенный стаб недопустим")
//            }
//        }
//        operation("Поиск подходящих предложений для объявления", MkplCommand.OFFERS) {
//            stubs("Обработка стабов") {
//                stubOffersSuccess("Имитация успешной обработки", corSettings)
//                stubValidationBadId("Имитация ошибки валидации id")
//                stubDbError("Имитация ошибки работы с БД")
//                stubNoCase("Ошибка: запрошенный стаб недопустим")
//            }
//        }
//    }.build()
//}
