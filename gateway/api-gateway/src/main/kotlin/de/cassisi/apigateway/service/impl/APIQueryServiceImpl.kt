package de.cassisi.apigateway.service.impl

import de.cassisi.apigateway.service.APIQueryService
import de.cassisi.apigateway.service.MetadataDocument
import de.cassisi.apigateway.service.SimpleMetadataDocument
import de.cassisi.apigateway.service.StudentDTO
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.util.*

@Service
class APIQueryServiceImpl(
    private val catalogueQueryService: WebClient,
    private val studentQueryService: WebClient,
    private val lendingQueryService: WebClient,
    private val chargingQueryService: WebClient,
    private val catalogueSearchQueryService: WebClient
) : APIQueryService {

    override fun getCatalogue(): List<MetadataDocument> {
        return catalogueQueryService.get()
            .uri("/all")
            .retrieve().bodyToMono<List<MetadataDocument>>().block()!!
    }

    override fun getStudentInformation(studentId: UUID): StudentDTO {
        // retrieve information from student query service
        val studentInformation = studentQueryService.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/")
                    .queryParam("studentId", studentId)
                    .build()
            }
            .retrieve().bodyToMono<StudentDocument>().block()!!

        // retrieve information from charging service

        val chargingData = chargingQueryService.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/")
                    .queryParam("studentId", studentId)
                    .build()
            }
            .retrieve()
            .bodyToMono<StudentChargingDocument>()
            .block()!!

        return StudentDTO(
            studentId,
            studentInformation.matriculationNumber,
            studentInformation.forename,
            studentInformation.lastname,
            studentInformation.email,
            studentInformation.matriculated,
            chargingData.charges
        )
    }

    data class StudentDocument(
        val studentId: String,
        val matriculationNumber: Int,
        val forename: String,
        val lastname: String,
        val email: String,
        val matriculated: Boolean
    )

    data class StudentChargingDocument(
        val studentId: String,
        val charges: Int
    )

    override fun searchByTitle(title: String): List<SimpleMetadataDocument> {
        return catalogueSearchQueryService.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("search")
                    .queryParam("title", title)
                    .build()
            }
            .retrieve().bodyToMono<List<SimpleMetadataDocument>>().block()!!
    }
}