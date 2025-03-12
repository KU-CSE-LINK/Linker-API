package com.linker.linkerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class LinkerApiApplication

fun main(args: Array<String>) {
    runApplication<LinkerApiApplication>(*args)
}
