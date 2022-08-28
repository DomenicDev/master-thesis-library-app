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
    fun addMetadata(@RequestBody request: AddMetadataRequest) {
        apiService.addMetadata(request.metadataId, request.title, request.author, request.isbn, request.publisher)
    }

    data class AddMetadataRequest(
        val metadataId: UUID,
        val title: String,
        val author: String,
        val isbn: String,
        val publisher: String
    )

}