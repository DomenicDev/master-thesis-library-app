package de.cassisi.chargingprojector.eventhandler


import de.cassisi.chargingprojector.dbmodel.StudentDocument
import de.cassisi.chargingprojector.dbmodel.StudentRepository
import de.cassisi.chargingprojector.subscription.SerializableStudentChargeAccountCreated
import de.cassisi.chargingprojector.subscription.SerializableStudentCharged
import de.cassisi.chargingprojector.subscription.SerializableStudentChargesPaid
import org.springframework.stereotype.Component

@Component
class EventHandler(
    private val studentRepository: StudentRepository
) {
    fun handle(event: SerializableStudentChargeAccountCreated) {
        val document = StudentDocument(
            event.id.toString(),
            event.charges
        )
        studentRepository.save(document)
    }

    fun handle(event: SerializableStudentCharged) {
        val old = studentRepository.findById(event.studentId.toString()).get()
        val new = old.copy(charges = event.currentCharges)
        studentRepository.save(new)
    }

    fun handle(event: SerializableStudentChargesPaid) {
        val old = studentRepository.findById(event.studentId.toString()).get()
        val new = old.copy(charges = event.currentCharges)
        studentRepository.save(new)
    }

}