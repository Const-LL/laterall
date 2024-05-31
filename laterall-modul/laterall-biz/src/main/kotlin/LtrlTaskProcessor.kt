package  ru.otus.otuskotlin.laterall.biz

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub

@Suppress("unused", "RedundantSuspendModifier")
class LtrlTaskProcessor(val corSettings: LtrlCorSettings) {
    suspend fun exec(ctx: LtrlContext) {
        ctx.taskResponse = LtrlTaskStub.get()
        ctx.tasksResponse = LtrlTaskStub.prepareCarTaskList("car task").toMutableList()
        ctx.state = LtrlState.RUNNING
    }
}
