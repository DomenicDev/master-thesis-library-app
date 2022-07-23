package de.cassisi.charging.integration

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import de.cassisi.charging.student.CreateChargingAccountCommand
import de.cassisi.charging.student.StudentId
import de.cassisi.charging.student.StudentService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.*

@Component
class StudentKafkaIntegration(private val service: StudentService) {

    private val gson = Gson()

    @KafkaListener(topics = ["students"])
    fun processMessage(
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) key: String,
        @Header("eventId") eventId: String,
        @Header("eventType") eventType: String,
        @Payload payload: String
    ) {
        if (eventType == "student-added") {
            val event = gson.fromJson(payload, SerializableStudentCreated::class.java)
            val studentId = StudentId(event.id)
            service.createChargingAccount(CreateChargingAccountCommand(studentId))
        }
    }

    // SERIALIZABLE DATA CLASSES --> FOR READING MESSAGES FROM KAFKA

    data class SerializableStudentCreated(
        @SerializedName("id")       val id: UUID,
    //    val name: SerializableName,
     //   @SerializedName("email")    val email: String,
     //   @SerializedName("matriculationNumber") val matriculationNumber: Int,
     //   @SerializedName("matriculationStatus")   val matriculationStatus: Boolean,
    )

/*    data class SerializableName(
        @SerializedName("forename") val forename: String,
        @SerializedName("lastname") val lastname: String
    )*/
}