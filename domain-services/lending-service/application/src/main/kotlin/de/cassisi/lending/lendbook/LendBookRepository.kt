package de.cassisi.lending.lendbook

import de.cassisi.lending.book.BookId
import de.cassisi.lending.student.StudentId

interface LendBookRepository {

    fun exists(studentId: StudentId): Boolean

    fun exists(bookId: BookId): Boolean

}