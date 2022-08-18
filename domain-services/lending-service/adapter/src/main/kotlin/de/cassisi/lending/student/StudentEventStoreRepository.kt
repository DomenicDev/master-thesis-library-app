package de.cassisi.lending.student

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.common.AbstractEventStoreRepository
import de.cassisi.lending.common.Version


open class StudentEventStoreRepository(client: EventStoreDBClient) : AbstractEventStoreRepository<Student,StudentId,StudentEvent, SerializableStudentEvent>(client){

    companion object {
        private const val STUDENT_ADDED = "student-added"
        private const val STUDENT_MATRICULATION_CHANGED = "student-matriculation-changed"
        private const val STUDENT_LOCK_STATUS_CHANGED = "student-lock-status-changed"
    }

    override fun createEmptyAggregate(id: StudentId, version: Version): Student {
        return StudentFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: StudentEvent): SerializableStudentEvent {
        return when (event) {
            is StudentCreated -> SerializableStudentCreated(
                event.id.uuid,
                event.matriculationStatus.status,
                event.lockStatus.locked
            )
            is StudentMatriculatedChanged -> SerializableStudentMatriculationChanged(
                event.studentId.uuid,
                event.matriculationStatus.status
            )
            is StudentLockStatusChanged -> SerializableStudentLockStatusChanged(
                event.student.uuid,
                event.lockStatus.locked
            )
        }
    }

    override fun convertToDomainEvent(raw: SerializableStudentEvent): StudentEvent {
        return when (raw) {
            is SerializableStudentCreated -> StudentCreated(
                StudentId(raw.id),
                MatriculationStatus(raw.matriculationStatus),
                LockStatus(raw.lockStatus)
            )
            is SerializableStudentMatriculationChanged -> StudentMatriculatedChanged(
                StudentId(raw.studentId),
                MatriculationStatus(raw.status)
            )
            is SerializableStudentLockStatusChanged -> StudentLockStatusChanged(
                StudentId(raw.studentId),
                LockStatus(raw.lockStatus)
            )
        }
    }

    override fun getSerializableEventType(eventType: String): Class<out SerializableStudentEvent> {
        return when (eventType) {
            STUDENT_ADDED -> SerializableStudentCreated::class.java
            STUDENT_MATRICULATION_CHANGED -> SerializableStudentMatriculationChanged::class.java
            STUDENT_LOCK_STATUS_CHANGED -> SerializableStudentLockStatusChanged::class.java
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
            is StudentLockStatusChanged -> STUDENT_LOCK_STATUS_CHANGED
        }
    }

}