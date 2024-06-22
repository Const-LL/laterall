package ru.otus.otuskotlin.laterall.api

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import ru.otus.otuskotlin.laterall.api.models.IRequest
import ru.otus.otuskotlin.laterall.api.models.IResponse
val apiMapper = JsonMapper.builder().run { 
    enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
    build()
}

@Suppress("unused")
fun apiRequestSerialize(request: IRequest): String = apiMapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST", "unused")
fun <T : IRequest> apiRequestDeserialize(json: String): T =
    apiMapper.readValue(json, IRequest::class.java) as T

@Suppress("unused")
fun apiResponseSerialize(response: IResponse): String = apiMapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST", "unused")
fun <T : IResponse> apiResponseDeserialize(json: String): T =
    apiMapper.readValue(json, IResponse::class.java) as T
