package de.cassisi.catalogue.campus

class CampusCommandHandler(private val repository: CampusRepository) {

    fun createCampus(command: CreateCampus) {
        val campus = CampusFactory.create(command)
        this.repository.save(campus)
    }

}