package ru.otus.otuskotlin.laterall.app.rabbit.controllers

import ru.otus.otuskotlin.laterall.app.rabbit.config.RabbitExchangeConfiguration

interface IRabbitMqController {
    val exchangeConfig: RabbitExchangeConfiguration
    suspend fun process()
    fun close()
}

