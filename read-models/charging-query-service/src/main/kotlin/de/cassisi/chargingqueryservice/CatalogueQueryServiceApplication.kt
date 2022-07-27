package de.cassisi.chargingqueryservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CatalogueQueryServiceApplication

fun main(args: Array<String>) {
    runApplication<CatalogueQueryServiceApplication>(*args)
}
