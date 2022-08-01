package de.cassisi.lending.book

import de.cassisi.lending.student.StudentId
import java.time.LocalDate

sealed interface Reservation

data class ActiveReservation(
    val studentId: StudentId,
    val reservedDate: LocalDate,
    val expireDate: LocalDate
): Reservation

object NoReservation : Reservation

