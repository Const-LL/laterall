package ru.otus.otuskotlin.laterall.app.common

import kotlinx.datetime.Clock
//import ru.otus.otuskotlin.laterall.api.log1.mapper.toLog
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.asLtrlError
import ru.otus.otuskotlin.laterall.common.models.LtrlState
//import kotlin.reflect.KClass

suspend inline fun <T> ILtrlAppSettings.controllerHelper(
    crossinline getRequest: suspend LtrlContext.() -> Unit,
    crossinline toResponse: suspend LtrlContext.() -> T,
//    clazz: KClass<*>,
//    logId: String,
): T {
//    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = LtrlContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
//        logger.info(
//            msg = "Request $logId started for ${clazz.simpleName}",
//            marker = "BIZ",
//            data = ctx.toLog(logId)
//        )
        processor.exec(ctx)
//        logger.info(
//            msg = "Request $logId processed for ${clazz.simpleName}",
//            marker = "BIZ",
//            data = ctx.toLog(logId)
//        )
        ctx.toResponse()
    } catch (e: Throwable) {
//        logger.error(
//            msg = "Request $logId failed for ${clazz.simpleName}",
//            marker = "BIZ",
//            data = ctx.toLog(logId)
//        )
        ctx.state = LtrlState.FAILING
        ctx.errors.add(e.asLtrlError())
        processor.exec(ctx)
        ctx.toResponse()
    }
}
