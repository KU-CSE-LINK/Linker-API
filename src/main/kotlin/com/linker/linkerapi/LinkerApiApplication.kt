package com.linker.linkerapi

import com.linker.linkerapi.locker.service.LockerService
import jakarta.annotation.PostConstruct
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
class LinkerApiApplication {
    @PostConstruct
    fun started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }

    @Bean
    fun init(lockerService: LockerService) = CommandLineRunner {
        lockerService.initializeLockerData()
    }
}

fun main(args: Array<String>) {
    runApplication<LinkerApiApplication>(*args)
}
