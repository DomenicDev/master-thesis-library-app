package de.cassisi.lending

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LendingServiceApplication

fun main(args: Array<String>) {
    runApplication<LendingServiceApplication>(*args)
}