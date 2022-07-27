package de.cassisi.catalogue.campus

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("campus")
class CampusController(private val campusCommandHandler: CampusCommandHandler) {

    @PostMapping
    fun addCampus(request: AddCampusRequest) {
        val campusId = CampusId(request.campusId)
        val campusLocation = CampusLocation(request.campusLocation)

        val command = CreateCampus(campusId, campusLocation)

        campusCommandHandler.createCampus(command)
    }


    data class AddCampusRequest(
        val campusId: UUID,
        val campusLocation: String
    )
}