package de.cassisi.lending.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.book.BookEventStoreRepository
import de.cassisi.lending.book.add.AddBook
import de.cassisi.lending.book.add.AddBookEventStoreRepository
import de.cassisi.lending.book.add.AddBookExecutor
import de.cassisi.lending.book.add.AddBookRepository
import de.cassisi.lending.book.borrow.BorrowBook
import de.cassisi.lending.book.borrow.BorrowBookEventStoreRepository
import de.cassisi.lending.book.borrow.BorrowBookExecutor
import de.cassisi.lending.book.borrow.BorrowBookRepository
import de.cassisi.lending.book.returnbook.ReturnBook
import de.cassisi.lending.book.returnbook.ReturnBookEventStoreRepository
import de.cassisi.lending.book.returnbook.ReturnBookExecutor
import de.cassisi.lending.book.returnbook.ReturnBookRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BookConfig {

    @Bean
    fun bookEventStoreRepository(eventStoreDBClient: EventStoreDBClient): BookEventStoreRepository {
        return BookEventStoreRepository(eventStoreDBClient)
    }

    @Bean
    fun addBookRepository(bookEventStoreRepository: BookEventStoreRepository): AddBookRepository {
        return AddBookEventStoreRepository(bookEventStoreRepository)
    }

    @Bean
    fun addBook(repository: AddBookRepository): AddBook {
        return AddBookExecutor(repository)
    }

    @Bean
    fun borrowBookRepository(repository: BookEventStoreRepository): BorrowBookRepository {
        return BorrowBookEventStoreRepository(repository)
    }

    @Bean
    fun borrowBook(repository: BorrowBookRepository): BorrowBook {
        return BorrowBookExecutor(repository)
    }

    @Bean
    fun returnBookRepository(repository: BookEventStoreRepository): ReturnBookRepository {
        return ReturnBookEventStoreRepository(repository)
    }

    @Bean
    fun returnBook(repository: ReturnBookRepository): ReturnBook {
        return ReturnBookExecutor(repository)
    }

}