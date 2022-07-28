package de.cassisi.studentprojector.eventhandler


import de.cassisi.studentprojector.dbmodel.StudentDocument
import de.cassisi.studentprojector.dbmodel.StudentRepository
import de.cassisi.studentprojector.subscription.SerializableStudentCreated
import de.cassisi.studentprojector.subscription.SerializableStudentMatriculationChanged
import org.springframework.stereotype.Component

@Component
class EventHandler(
    private val studentRepository: StudentRepository
) {

    fun handle(event: SerializableStudentCreated) {
        val document = StudentDocument(
            event.studentId.toString(),
            event.matriculationNumber,
            event.name.forename,
            event.name.lastname,
            event.email,
            event.matriculationStatus
        )
        studentRepository.save(document)
    }

    fun handle(event: SerializableStudentMatriculationChanged) {
        val student = studentRepository.findById(event.studentId.toString()).get()
        val updatedStudent = student.copy(matriculated = event.status)
        studentRepository.save(updatedStudent)
    }

}