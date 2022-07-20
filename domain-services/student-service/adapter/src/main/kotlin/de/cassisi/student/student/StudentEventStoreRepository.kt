package de.cassisi.student.student

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.student.student.common.AbstractEventStoreRepository
import de.cassisi.student.common.Version


open class StudentEventStoreRepository(client: EventStoreDBClient) : AbstractEventStoreRepository<Student, StudentId, StudentEvent, SerializableStudentEvent>(client){

    companion object {
        private const val STUDENT_ADDED = "student-added"
        private const val STUDENT_MATRICULATION_CHANGED = "student-matriculation-changed"
    }

    override fun createEmptyAggregate(id: StudentId, version: Version): Student {
        return StudentFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: StudentEvent): SerializableStudentEvent {
        return when (event) {
            is StudentCreated -> SerializableStudentCreated(
                event.id.uuid,
                SerializableName(event.name.firstname, event.name.lastName),
                event.email.email,
                event.matriculationNumber.matriculationNumber,
                event.matriculationStatus.status,
            )
            is StudentMatriculatedChanged -> SerializableStudentMatriculationChanged(
                event.studentId.uuid,
                event.matriculationStatus.status
            )
        }
    }

    override fun convertToDomainEvent(raw: SerializableStudentEvent): StudentEvent {
        return when (raw) {
            is SerializableStudentCreated -> StudentCreated(
                StudentId(raw.id),
                Name(raw.name.forename, raw.name.lastname),
                Email(raw.email),
                MatriculationNumber(raw.matriculationNumber),
                MatriculationStatus(raw.matriculationStatus),
            )
            is SerializableStudentMatriculationChanged -> StudentMatriculatedChanged(
                StudentId(raw.studentId),
                MatriculationStatus(raw.status)
            )
        }
    }

    override fun getSerializableEventType(eventType: String): Class<out SerializableStudentEvent> {
        return when (eventType) {
            STUDENT_ADDED -> SerializableStudentCreated::class.java
            STUDENT_MATRICULATION_CHANGED -> SerializableStudentMatriculationChanged::class.java
            else -> throw IllegalArgumentException()
        }
    }

    override fun toStreamName(id: StudentId): String {
        return "student-${id.uuid}"
    }

    override fun getEventTypeName(event: StudentEvent): String {
        return when (event) {
            is StudentCreated -> STUDENT_ADDED
            is StudentMatriculatedChanged -> STUDENT_MATRICULATION_CHANGED
        }
    }
}