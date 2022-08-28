package de.cassisi.apigateway.controller

import de.cassisi.apigateway.service.APIService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/campus")
class CampusController(private val apiService: APIService) {

    @PostMapping
    fun addCampus(@RequestBody request: AddCampusRequest): ResponseEntity<Unit> {
        apiService.addCampus(request.campusId, request.campus)
        return ResponseEntity.ok().build()
    }

    data class AddCampusRequest(
        val campusId: UUID,
        val campus: String
    )

}