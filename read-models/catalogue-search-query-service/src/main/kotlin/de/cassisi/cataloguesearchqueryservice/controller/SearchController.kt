package de.cassisi.cataloguesearchqueryservice.controller

import de.cassisi.cataloguesearchqueryservice.dbmodel.MetadataDocument
import de.cassisi.cataloguesearchqueryservice.service.SearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(private val service: SearchService) {

    @GetMapping("/search")
    fun searchTitle(@RequestParam title: String): ResponseEntity<List<MetadataDocument>> {
        return ResponseEntity.ok(service.searchTitle(title))
    }

}