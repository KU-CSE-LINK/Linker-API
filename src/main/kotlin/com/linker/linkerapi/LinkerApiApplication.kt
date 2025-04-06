package com.linker.linkerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
class LinkerApiApplication

fun main(args: Array<String>) {
    runApplication<LinkerApiApplication>(*args)
}
