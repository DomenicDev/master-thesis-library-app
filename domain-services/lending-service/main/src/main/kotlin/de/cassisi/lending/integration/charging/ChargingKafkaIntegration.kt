package de.cassisi.lending.integration.charging

import com.google.gson.Gson
import de.cassisi.lending.student.Charges
import de.cassisi.lending.student.StudentId
import de.cassisi.lending.student.StudentService
import de.cassisi.lending.student.UpdateStudentChargesCommand
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

    @KafkaListener(topics = ["charges"])
    fun process(
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) key: String,
        @Header("eventId") eventId: String,
        @Header("eventType") eventType: String,
        @Payload payload: String
    ) {
        logger.info("processing charging message with key: $key")
        val event = gson.fromJson(payload, SimpleChargingIntegrationEvent::class.java)
        val studentId = StudentId(event.studentId)
        val currentCharges = Charges(event.currentCharges)
        val command = UpdateStudentChargesCommand(studentId, currentCharges)
        studentService.updateStudentCharges(command)
    }

    data class SimpleChargingIntegrationEvent(
        val studentId: UUID,
        val currentCharges: Int
    )

}