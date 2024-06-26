package  ru.otus.otuskotlin.laterall.biz

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.biz.general.initStatus
import ru.otus.otuskotlin.laterall.biz.general.operation
import ru.otus.otuskotlin.laterall.biz.general.stubs
import ru.otus.otuskotlin.laterall.biz.stubs.*
import ru.otus.otuskotlin.laterall.cor.rootChain
import ru.otus.otuskotlin.laterall.common.models.LtrlCommand
import ru.otus.otuskotlin.laterall.biz.validation.*
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskId
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskLock

@Suppress("unused", "RedundantSuspendModifier")
class LtrlTaskProcessor(private val corSettings: LtrlCorSettings = LtrlCorSettings.NONE) {
    suspend fun exec(ctx: LtrlContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<LtrlContext> {
        initStatus("Инициализация статуса")
        operation("Создание задачи", LtrlCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в taskValidating") { taskValidating = taskRequest.deepCopy() }
                worker("Очистка id") { taskValidating.id = LtrlTaskId.NONE }
                worker("Очистка заголовка") { taskValidating.title = taskValidating.title.trim() }
                worker("Очистка описания") { taskValidating.description = taskValidating.description.trim() }
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                validateTitleHasContent("Проверка символов")
                validateDescriptionNotEmpty("Проверка, что описание не пусто")
                validateDescriptionHasContent("Проверка символов")

                finishTaskValidation("Завершение проверок")
            }
        }
        operation("Получить задачу", LtrlCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в taskValidating") { taskValidating = taskRequest.deepCopy() }
                worker("Очистка id") { taskValidating.id = LtrlTaskId(taskValidating.id.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                finishTaskValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Удалить задачу", LtrlCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в taskValidating") {
                    taskValidating = taskRequest.deepCopy()
                }
                worker("Очистка id") { taskValidating.id = LtrlTaskId(taskValidating.id.asString().trim()) }
                worker("Очистка lock") { taskValidating.lock = LtrlTaskLock(taskValidating.lock.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
                finishTaskValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Изменить объявление", LtrlCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в taskValidating") { taskValidating = taskRequest.deepCopy() }
                worker("Очистка id") { taskValidating.id = LtrlTaskId(taskValidating.id.asString().trim()) }
                worker("Очистка lock") { taskValidating.lock = LtrlTaskLock(taskValidating.lock.asString().trim()) }
                worker("Очистка заголовка") { taskValidating.title = taskValidating.title.trim() }
                worker("Очистка описания") { taskValidating.description = taskValidating.description.trim() }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
                validateTitleNotEmpty("Проверка на непустой заголовок")
                validateTitleHasContent("Проверка на наличие содержания в заголовке")
                validateDescriptionNotEmpty("Проверка на непустое описание")
                validateDescriptionHasContent("Проверка на наличие содержания в описании")

                finishTaskValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Поиск объявлений", LtrlCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в taskFilterValidating") { taskFilterValidating = taskFilterRequest.deepCopy() }
                validateSearchStringLength("Валидация длины строки поиска в фильтре")

                finishTaskFilterValidation("Успешное завершение процедуры валидации")
            }
        }
    }.build()
}