package de.cassisi.cataloguequeryservice.controller

import de.cassisi.cataloguequeryservice.model.MetadataDocument
import de.cassisi.cataloguequeryservice.service.QueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QueryController(private val queryService: QueryService) {

    @GetMapping("/all")
    fun getAll(): List<MetadataDocument> {
        return queryService.getAll().toList()
    }

}