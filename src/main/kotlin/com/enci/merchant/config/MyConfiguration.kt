package com.enci.merchant.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.concurrent.Executors
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import java.util.concurrent.Executor


@Configuration
@EnableScheduling
@PropertySource(value = "classpath:configs/config.properties")
class MyConfiguration {

    @Bean
    fun propertyConfigInDev(): PropertySourcesPlaceholderConfigurer = PropertySourcesPlaceholderConfigurer()

    fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.setScheduler(TaskExecutor())
    }

    @Bean
    fun TaskExecutor(): Executor {
        return Executors.newScheduledThreadPool(100)
    }

}