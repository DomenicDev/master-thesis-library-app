package de.cassisi.lending.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.book.BookEventStoreRepository
import de.cassisi.lending.book.borrow.BorrowBook
import de.cassisi.lending.book.returnbook.ReturnBook
import de.cassisi.lending.lendbook.LendBook
import de.cassisi.lending.lendbook.LendBookEventStoreRepository
import de.cassisi.lending.lendbook.LendBookExecutor
import de.cassisi.lending.lendbook.LendBookRepository
import de.cassisi.lending.loan.create.CreateLoan
import de.cassisi.lending.student.StudentEventStoreRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LendBookConfig {

    @Bean
    fun lendBookRepository(client: EventStoreDBClient, studentRepository: StudentEventStoreRepository, bookRepository: BookEventStoreRepository): LendBookRepository {
        return LendBookEventStoreRepository(client, studentRepository, bookRepository)
    }

    @Bean
    fun lendBook(borrowBook: BorrowBook, returnBook: ReturnBook, createLoan: CreateLoan, repository: LendBookRepository): LendBook {
        return LendBookExecutor(borrowBook, returnBook, createLoan, repository)
    }

}