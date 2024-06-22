package ru.otus.otuskotlin.laterall.common.exceptions

import ru.otus.otuskotlin.laterall.common.models.LtrlCommand


class UnknownLtrlCommand(command: LtrlCommand) : Throwable("Wrong command $command at mapping toTransport stage")
