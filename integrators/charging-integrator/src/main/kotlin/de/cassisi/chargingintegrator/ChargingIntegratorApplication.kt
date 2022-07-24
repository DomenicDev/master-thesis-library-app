package de.cassisi.chargingintegrator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChargingIntegratorApplication

fun main(args: Array<String>) {
    runApplication<ChargingIntegratorApplication>(*args)
}
