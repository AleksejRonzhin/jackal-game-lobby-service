package ru.rsreu.jackal.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class TaskSchedulerConfiguration {
    @Bean("threadPoolTaskScheduler")
    fun threadPoolTaskScheduler(): TaskScheduler {
        val taskScheduler = ThreadPoolTaskScheduler()
        taskScheduler.poolSize = 100
        return taskScheduler
    }
}