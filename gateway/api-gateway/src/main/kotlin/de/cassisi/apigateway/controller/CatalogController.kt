package de.cassisi.apigateway.controller

import de.cassisi.apigateway.service.APIQueryService
import de.cassisi.apigateway.service.MetadataDocument
import de.cassisi.apigateway.service.SimpleMetadataDocument
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog")
class CatalogController(private val apiQueryService: APIQueryService) {

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<MetadataDocument>> {
        return ResponseEntity.ok(apiQueryService.getCatalogue())
    }

    @GetMapping("/search")
    fun searchByTitle(@RequestParam title: String): ResponseEntity<List<SimpleMetadataDocument>> {
        return ResponseEntity.ok(apiQueryService.searchByTitle(title))
    }

}