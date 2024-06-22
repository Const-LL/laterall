package ru.otus.otuskotlin.laterall.stubs

import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskId
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStubEntities.TASK_CAR_ENTITY1


object LtrlTaskStub {
    fun get(): LtrlTask = TASK_CAR_ENTITY1.copy()

    fun prepareResult(block: LtrlTask.() -> Unit): LtrlTask = get().apply(block)

    fun prepareCarTaskList(filter: String) = listOf(
        ltrlTaskCar("d-123-01", filter),
        ltrlTaskCar("d-123-02", filter),
        ltrlTaskCar("d-123-03", filter),
        ltrlTaskCar("d-123-04", filter),
        ltrlTaskCar("d-123-05", filter),
        ltrlTaskCar("d-123-06", filter),
    )

    private fun ltrlTaskCar(id: String, filter: String) =
        ltrlTask(TASK_CAR_ENTITY1, id = id, filter = filter)


    private fun ltrlTask(base: LtrlTask, id: String, filter: String) = base.copy(
        id = LtrlTaskId(id),
        title = "$filter $id",
        description = "desc $filter $id",
    )

}
