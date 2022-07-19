package ru.rsreu.jackal.api.lobby.service.connection_checking

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ScheduledFuture

@Component
class UserConnectionChecker(
    @Qualifier("threadPoolTaskScheduler")
    private val taskScheduler: TaskScheduler,

    @Value("\${web_sockets.connection_checker_delay-s}")
    private val checkingDelay: Long
) {
    fun addCheckConnectionTask(checkTask: UserConnectionCheckTask): ScheduledFuture<*> {
        return taskScheduler.schedule(checkTask, Date().toInstant().plusSeconds(checkingDelay))
    }
}