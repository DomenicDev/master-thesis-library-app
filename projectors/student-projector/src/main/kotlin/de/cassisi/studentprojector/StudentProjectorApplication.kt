package de.cassisi.studentprojector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StudentProjectorApplication

fun main(args: Array<String>) {
    runApplication<StudentProjectorApplication>(*args)
}
