package ru.otus.otuskotlin.laterall.mappers.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to LtrlContext")
