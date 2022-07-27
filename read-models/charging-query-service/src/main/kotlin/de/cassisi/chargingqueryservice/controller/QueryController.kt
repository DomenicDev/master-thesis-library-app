package de.cassisi.chargingqueryservice.controller

import de.cassisi.chargingqueryservice.model.StudentDocument
import de.cassisi.chargingqueryservice.service.QueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class QueryController(private val queryService: QueryService) {

    @GetMapping("/all")
    fun getAll(): List<StudentDocument> {
        return queryService.getAll().toList()
    }

    @GetMapping
    fun getStudent(@RequestParam studentId: UUID): StudentDocument {
        return queryService.getById(studentId.toString())
    }

}