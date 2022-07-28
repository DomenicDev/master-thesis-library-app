package de.cassisi.apigateway.controller

import de.cassisi.apigateway.service.APIQueryService
import de.cassisi.apigateway.service.APIService
import de.cassisi.apigateway.service.MetadataDocument
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/metadata")
class MetadataController(private val apiService: APIService, private val apiQueryService: APIQueryService) {

    @PostMapping("/metadata")
    fun addMetadata(@RequestBody request: AddMetadataRequest): ResponseEntity<String> {
        val metadataId = UUID.randomUUID()
        apiService.addMetadata(metadataId, request.title, request.author, request.isbn, request.publisher)
        return ResponseEntity.ok("$metadataId")
    }

    data class AddMetadataRequest(
        val title: String,
        val author: String,
        val isbn: String,
        val publisher: String
    )

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<MetadataDocument>> {
        return ResponseEntity.ok(apiQueryService.getCatalogue())
    }

}