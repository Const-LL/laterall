package ru.otus.otuskotlin.laterall.common

//import ru.otus.otuskotlin.laterall.logging.common.MpLoggerProvider

data class LtrlCorSettings(
    val loggerProvider:String = "nologs" //пока заглушка просто todo вставить логи
//    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
) {
    companion object {
        val NONE = LtrlCorSettings()
    }
}
