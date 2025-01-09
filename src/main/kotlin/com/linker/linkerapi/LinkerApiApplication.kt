package com.linker.linkerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LinkerApiApplication

fun main(args: Array<String>) {
    runApplication<LinkerApiApplication>(*args)
}
