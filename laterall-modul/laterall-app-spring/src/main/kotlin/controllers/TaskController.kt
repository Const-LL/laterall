package ru.otus.otuskotlin.laterall.app.spring.controllers

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.laterall.api.models.*
import ru.otus.otuskotlin.laterall.app.common.controllerHelper
import ru.otus.otuskotlin.laterall.mappers.fromTransport
import ru.otus.otuskotlin.laterall.mappers.toTransportTask
import ru.otus.otuskotlin.laterall.app.spring.config.LtrlAppSettings

import kotlin.reflect.KClass

@Suppress("unused")
@RestController
@RequestMapping("task")
class TaskController(
    private val appSettings: LtrlAppSettings
) {

    @PostMapping("create")
    suspend fun create(@RequestBody request: TaskCreateRequest): TaskCreateResponse =
        process(appSettings, request = request, this::class, "create")

    @PostMapping("read")
    suspend fun  read(@RequestBody request: TaskReadRequest): TaskReadResponse =
        process(appSettings, request = request, this::class, "read")

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun  update(@RequestBody request: TaskUpdateRequest): TaskUpdateResponse =
        process(appSettings, request = request, this::class, "update")

    @PostMapping("delete")
    suspend fun  delete(@RequestBody request: TaskDeleteRequest): TaskDeleteResponse =
        process(appSettings, request = request, this::class, "delete")

    @PostMapping("search")
    suspend fun  search(@RequestBody request: TaskSearchRequest): TaskSearchResponse =
        process(appSettings, request = request, this::class, "search")


    companion object {
        suspend inline fun <reified Q : IRequest, reified R : IResponse> process(
            appSettings: LtrlAppSettings,
            request: Q,
            clazz: KClass<*>,
            logId: String,
        ): R = appSettings.controllerHelper(
            {
                fromTransport(request)
            },
            { toTransportTask() as R },
//            clazz,
//            logId,
        )
    }
}
