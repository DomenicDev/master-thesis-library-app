package de.cassisi.lending.integration.student

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import de.cassisi.lending.student.MatriculationStatus
import de.cassisi.lending.student.StudentId
import de.cassisi.lending.student.StudentService
import de.cassisi.lending.student.UpdateMatriculationStatusCommand
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.*

@Component
class StudentKafkaIntegration(private val studentService: StudentService) {

    companion object {
        private val logger = LoggerFactory.getLogger(StudentKafkaIntegration::class.java)

        // EVENT TYPES
        private const val STUDENT_MATRICULATION_CHANGED = "student-matriculation-changed"
    }

    private val gson = Gson()

    @KafkaListener(topics = ["students"])
    fun process(
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) key: String,
        @Header("eventId") eventId: String,
        @Header("eventType") eventType: String,
        @Payload payload: String
    ) {
        logger.info("processing message from stream $key")
        when (eventType) {
            STUDENT_MATRICULATION_CHANGED -> {
                val event = gson.fromJson(payload, SerializableStudentMatriculationChanged::class.java)
                val studentId = StudentId(event.studentId)
                val status = MatriculationStatus(event.status)
                updateMatriculationStatus(studentId, status)
            }
        }
    }

    private fun updateMatriculationStatus(studentId: StudentId, status: MatriculationStatus) {
        val updateCommand = UpdateMatriculationStatusCommand(studentId, status)
        studentService.updateMatriculationStatus(updateCommand)
    }

    data class SerializableStudentMatriculationChanged(
        @SerializedName("studentId") val studentId: UUID,
        @SerializedName("matriculationStatus") val status: Boolean
    )

}