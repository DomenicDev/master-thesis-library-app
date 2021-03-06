package de.cassisi.lending.student

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.common.AbstractEventStoreRepository
import de.cassisi.lending.common.Version


open class StudentEventStoreRepository(client: EventStoreDBClient) : AbstractEventStoreRepository<Student,StudentId,StudentEvent, SerializableStudentEvent>(client){

    companion object {
        private const val STUDENT_ADDED = "student-added"
        private const val STUDENT_MATRICULATION_CHANGED = "student-matriculation-changed"
        private const val STUDENT_CHARGE_CHANGED = "student-charge-changed"
    }

    override fun createEmptyAggregate(id: StudentId, version: Version): Student {
        return StudentFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: StudentEvent): SerializableStudentEvent {
        return when (event) {
            is StudentCreated -> SerializableStudentCreated(
                event.id.uuid,
                event.matriculationStatus.status,
                event.charges.amount
            )
            is StudentMatriculatedChanged -> SerializableStudentMatriculationChanged(
                event.studentId.uuid,
                event.matriculationStatus.status
            )
            is StudentChargesChanged -> SerializableStudentChargesChanged(
                event.student.uuid,
                event.newCharges.amount
            )
        }
    }

    override fun convertToDomainEvent(raw: SerializableStudentEvent): StudentEvent {
        return when (raw) {
            is SerializableStudentCreated -> StudentCreated(
                StudentId(raw.id),
                MatriculationStatus(raw.matriculationStatus),
                Charges(raw.charges)
            )
            is SerializableStudentMatriculationChanged -> StudentMatriculatedChanged(
                StudentId(raw.studentId),
                MatriculationStatus(raw.status)
            )
            is SerializableStudentChargesChanged -> StudentChargesChanged(
                StudentId(raw.studentId),
                Charges(raw.newCharges)
            )
        }
    }

    override fun getSerializableEventType(eventType: String): Class<out SerializableStudentEvent> {
        return when (eventType) {
            STUDENT_ADDED -> SerializableStudentCreated::class.java
            STUDENT_MATRICULATION_CHANGED -> SerializableStudentMatriculationChanged::class.java
            STUDENT_CHARGE_CHANGED -> SerializableStudentMatriculationChanged::class.java
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
            is StudentChargesChanged -> STUDENT_CHARGE_CHANGED
        }
    }

}