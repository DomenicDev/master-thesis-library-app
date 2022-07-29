package de.cassisi.cataloguesearchprojector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CatalogueProjectorApplication

fun main(args: Array<String>) {
    runApplication<CatalogueProjectorApplication>(*args)
}
