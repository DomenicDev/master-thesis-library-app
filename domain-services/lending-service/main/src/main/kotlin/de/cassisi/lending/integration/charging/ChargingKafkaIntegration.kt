package de.cassisi.lending.integration.charging

import com.google.gson.Gson
import de.cassisi.lending.student.StudentId
import de.cassisi.lending.student.StudentService
import de.cassisi.lending.student.lock.LockStudentCommand
import de.cassisi.lending.student.lock.UnlockStudentCommand
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.*

@Component
class ChargingKafkaIntegration(private val studentService: StudentService) {

    companion object {
        private val logger = LoggerFactory.getLogger(ChargingKafkaIntegration::class.java)
    }

    private val gson = Gson()

    @KafkaListener(topics = ["charging"])
    fun process(
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) key: String,
        @Header("eventId") eventId: String,
        @Header("eventType") eventType: String,
        @Payload payload: String
    ) {
        logger.info("processing charging message with key: $key")

        when (eventType) {
            "lending-violation-occurred" -> {
                val event = gson.fromJson(payload, LendingViolationOccurredEvent::class.java)
                handle(event)
            }
            "lending-violation-resolved" -> {
                val event = gson.fromJson(payload, LendingViolationResolvedEvent::class.java)
                handle(event)
            }
        }
    }

    private fun handle(event: LendingViolationOccurredEvent) {
        val studentId = StudentId(event.studentId)
        val command = LockStudentCommand(studentId)
        studentService.lockStudent(command)
    }

    private fun handle(event: LendingViolationResolvedEvent) {
        val studentId = StudentId(event.studentId)
        val command = UnlockStudentCommand(studentId)
        studentService.unlockStudent(command)
    }

    data class LendingViolationOccurredEvent(
        val studentId: UUID
    )

    data class LendingViolationResolvedEvent(
        val studentId: UUID
    )
}

