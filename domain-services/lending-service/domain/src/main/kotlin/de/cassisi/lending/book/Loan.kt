package de.cassisi.lending.book

import de.cassisi.lending.student.StudentId
import java.time.LocalDate

class Loan(
    val loanId: LoanId,
    val studentId: StudentId,
    var startDate: LocalDate,
    var endDate: LocalDate,
    var extensions: Int
    ) {

    companion object {
        private const val MAX_EXTENSIONS = 3
    }


    fun incrementExtension() {
        this.extensions++
    }

    fun maximumOfExtensionsReached(): Boolean {
        return (extensions >= MAX_EXTENSIONS)
    }

}