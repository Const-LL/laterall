package ru.otus.otuskotlin.laterall.app.rabbit.controllers

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import ru.otus.otuskotlin.laterall.api.apiMapper
import ru.otus.otuskotlin.laterall.api.models.IRequest
import ru.otus.otuskotlin.laterall.app.common.controllerHelper
import ru.otus.otuskotlin.laterall.app.rabbit.config.LtrlAppSettings
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.asLtrlError
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.mappers.fromTransport
import ru.otus.otuskotlin.laterall.mappers.toTransportTask

// наследник RabbitProcessorBase, увязывает транспортную и бизнес-части
class RabbitDirectController(
    private val appSettings: LtrlAppSettings,
) : RabbitProcessorBase(
    rabbitConfig = appSettings.rabbit,
    exchangeConfig = appSettings.controllersConfig,
//    loggerProvider = appSettings.corSettings.loggerProvider,
) {
    override suspend fun Channel.processMessage(message: Delivery) {
        appSettings.controllerHelper(
            {
                val req = apiMapper.readValue(message.body, IRequest::class.java)
                fromTransport(req)
            },
            {
                val res = toTransportTask()
                apiMapper.writeValueAsBytes(res).also {
                    basicPublish(exchangeConfig.exchange, exchangeConfig.keyOut, null, it)
                }
            },
//            this@RabbitDirectController::class,
//            "rabbitmq-processor"
        )
    }

    override fun Channel.onError(e: Throwable, delivery: Delivery) {
        val context = LtrlContext()
        e.printStackTrace()
        context.state = LtrlState.FAILING
        context.errors.add(e.asLtrlError())
        val response = context.toTransportTask()
        apiMapper.writeValueAsBytes(response).also {
            basicPublish(exchangeConfig.exchange, exchangeConfig.keyOut, null, it)
        }
    }
}
