package de.cassisi.lending.lendbook

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.book.BookEventStoreRepository
import de.cassisi.lending.book.BookId
import de.cassisi.lending.student.StudentEventStoreRepository
import de.cassisi.lending.student.StudentId

class LendBookEventStoreRepository(
    private val client: EventStoreDBClient,
    private val studentRepository: StudentEventStoreRepository,
    private val bookRepository: BookEventStoreRepository
    ) : LendBookRepository {

    override fun exists(studentId: StudentId): Boolean {
        val streamId = studentRepository.toStreamName(studentId)
        return runCatching { client.readStream(streamId).get() }.isSuccess
    }

    override fun exists(bookId: BookId): Boolean {
        val streamId = bookRepository.toStreamName(bookId)
        return kotlin.runCatching { client.readStream(streamId).get() }.isSuccess
    }
}