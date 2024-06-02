package ru.otus.otuskotlin.laterall.app.rabbit

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.AfterClass
import org.junit.BeforeClass
import org.testcontainers.containers.RabbitMQContainer
import ru.otus.otuskotlin.laterall.api.apiMapper
import ru.otus.otuskotlin.laterall.api.models.TaskCreateObject
import ru.otus.otuskotlin.laterall.api.models.TaskCreateRequest
import ru.otus.otuskotlin.laterall.api.models.TaskCreateResponse
import ru.otus.otuskotlin.laterall.api.models.TaskDebug
import ru.otus.otuskotlin.laterall.api.models.TaskRequestDebugMode
import ru.otus.otuskotlin.laterall.api.models.TaskRequestDebugStubs
import ru.otus.otuskotlin.laterall.app.rabbit.config.LtrlAppSettings
import ru.otus.otuskotlin.laterall.app.rabbit.config.RabbitConfig
import ru.otus.otuskotlin.laterall.app.rabbit.config.RabbitExchangeConfiguration
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

//  тесты с использованием testcontainers
internal class RabbitMqTest {

    companion object {
        const val exchange = "test-exchange"
        const val exchangeType = "direct"
        const val RMQ_PORT = 5672

        private val container = run {
//            Этот образ предназначен для дебагинга, он содержит панель управления на порту httpPort
//            RabbitMQContainer("rabbitmq:3-management").apply {
//            Этот образ минимальный и не содержит панель управления
            RabbitMQContainer("rabbitmq:latest").apply {
//                withExposedPorts(5672, 15672) // Для 3-management
                withExposedPorts(RMQ_PORT)
            }
        }

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            container.start()
        }

        @AfterClass
        @JvmStatic
        fun afterAll() {
            container.stop()
        }
    }

    private val appSettings = LtrlAppSettings(
        rabbit = RabbitConfig(
            port = container.getMappedPort(RMQ_PORT)
        ),
//        corSettings = LtrlCorSettings(loggerProvider = MpLoggerProvider { mpLoggerLogback(it) }),
        controllersConfig = RabbitExchangeConfiguration(
            keyIn = "in-v1",
            keyOut = "out-v1",
            exchange = exchange,
            queue = "v1-queue",
            consumerTag = "v1-consumer-test",
            exchangeType = exchangeType
        ),
    )
    private val app = RabbitApp(appSettings = appSettings)

    @BeforeTest
    fun tearUp() {
        app.start()
    }

    @AfterTest
    fun tearDown() {
        println("Test is being stopped")
        app.close()
    }

    @Test
    fun taskCreateTest() {
        val (keyOut, keyIn) = with(appSettings.controllersConfig) { Pair(keyOut, keyIn) }
        val (tstHost, tstPort) = with(appSettings.rabbit) { Pair(host, port) }
        ConnectionFactory().apply {
            host = tstHost
            port = tstPort
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println(" [x] Received by $consumerTag: '$responseJson'")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

                channel.basicPublish(exchange, keyIn, null, apiMapper.writeValueAsBytes(taskCarCreate))

                runBlocking {
                    withTimeoutOrNull(1000L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("RESPONSE: $responseJson")
                val response = apiMapper.readValue(responseJson, TaskCreateResponse::class.java)
                val expected = LtrlTaskStub.get()

                assertEquals(expected.title, response.task?.title)
                assertEquals(expected.description, response.task?.description)
            }
        }
    }


    private val taskCarCreate = with(LtrlTaskStub.get()) {
        TaskCreateRequest(
            task = TaskCreateObject(
                title = title,
                description = description
            ),
            requestType = "create",
            debug = TaskDebug(
                mode = TaskRequestDebugMode.STUB,
                stub = TaskRequestDebugStubs.SUCCESS
            )
        )
    }
}
