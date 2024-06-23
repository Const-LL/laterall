package ru.otus.otuskotlin.laterall.common

//import ru.otus.otuskotlin.laterall.logging.common.MpLoggerProvider
import ru.otus.otuskotlin.laterall.common.repo.IRepoTask

data class LtrlCorSettings(
    val loggerProvider:String = "nologs", //пока заглушка просто todo вставить логи
//    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val repoStub: IRepoTask = IRepoTask.NONE,
    val repoTest: IRepoTask = IRepoTask.NONE,
    val repoProd: IRepoTask = IRepoTask.NONE
) {
    companion object {
        val NONE = LtrlCorSettings()
    }
}
