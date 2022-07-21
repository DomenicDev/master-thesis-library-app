package de.cassisi.catalogueintegrator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CatalogueIntegratorApplication

fun main(args: Array<String>) {
    runApplication<CatalogueIntegratorApplication>(*args)
}
