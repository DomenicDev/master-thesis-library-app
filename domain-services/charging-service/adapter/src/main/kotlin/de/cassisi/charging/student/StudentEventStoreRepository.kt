package de.cassisi.charging.student

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.charging.common.AbstractEventStoreRepository
import de.cassisi.charging.common.Version


open class StudentEventStoreRepository(client: EventStoreDBClient) :
    AbstractEventStoreRepository<Student, StudentId, StudentEvent, SerializableStudentEvent>(client) {

    companion object {
        private const val STUDENT_CHARGE_ACCOUNT_CREATED = "student-charge-account-created"
        private const val STUDENT_CHARGED = "student-charged"
        private const val STUDENT_CHARGES_PAID = "student-charges-paid"
    }

    override fun createEmptyAggregate(id: StudentId, version: Version): Student {
        return StudentFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: StudentEvent): SerializableStudentEvent {
        return when (event) {
            is StudentChargeAccountCreated -> SerializableStudentChargeAccountCreated(
                event.id.uuid,
                event.charges.charges
            )
            is StudentCharged -> SerializableStudentCharged(
                event.studentId.uuid,
                event.currentCharges.charges
            )
            is StudentChargesPaid -> SerializableStudentChargesPaid(
                event.studentId.uuid,
                event.currentCharges.charges
            )
        }
    }

    override fun convertToDomainEvent(raw: SerializableStudentEvent): StudentEvent {
        return when (raw) {
            is SerializableStudentChargeAccountCreated -> StudentChargeAccountCreated(
                StudentId(raw.id),
                Charges(raw.charges)
            )
            is SerializableStudentCharged -> StudentCharged(
                StudentId(raw.studentId),
                Charges(raw.currentCharges)
            )
            is SerializableStudentChargesPaid -> StudentChargesPaid(
                StudentId(raw.studentId),
                Charges(raw.currentCharges)
            )
        }
    }

    override fun getSerializableEventType(eventType: String): Class<out SerializableStudentEvent> {
        return when (eventType) {
            STUDENT_CHARGE_ACCOUNT_CREATED -> SerializableStudentChargeAccountCreated::class.java
            STUDENT_CHARGED -> SerializableStudentCharged::class.java
            STUDENT_CHARGES_PAID -> SerializableStudentChargesPaid::class.java
            else -> throw IllegalArgumentException()
        }
    }

    override fun toStreamName(id: StudentId): String {
        return "charges-${id.uuid}"
    }

    override fun getEventTypeName(event: StudentEvent): String {
        return when (event) {
            is StudentChargeAccountCreated -> STUDENT_CHARGE_ACCOUNT_CREATED
            is StudentCharged -> STUDENT_CHARGED
            is StudentChargesPaid -> STUDENT_CHARGES_PAID
        }
    }
}