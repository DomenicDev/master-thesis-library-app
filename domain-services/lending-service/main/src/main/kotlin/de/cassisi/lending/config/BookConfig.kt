package de.cassisi.lending.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.book.*
import de.cassisi.lending.book.add.AddBook
import de.cassisi.lending.book.add.AddBookEventStoreRepository
import de.cassisi.lending.book.add.AddBookExecutor
import de.cassisi.lending.book.add.AddBookRepository
import de.cassisi.lending.book.borrow.*
import de.cassisi.lending.book.extend.*
import de.cassisi.lending.book.returnbook.ReturnBook
import de.cassisi.lending.book.returnbook.ReturnBookEventStoreRepository
import de.cassisi.lending.book.returnbook.ReturnBookExecutor
import de.cassisi.lending.book.returnbook.ReturnBookRepository
import de.cassisi.lending.student.StudentEventStoreRepository
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
    fun borrowBookPolicyRepository(repository: StudentEventStoreRepository): BorrowBookPolicyRepository {
        return BorrowBookPolicyEventStoreRepository(repository)
    }

    @Bean
    fun borrowBookPolicy(repository: BorrowBookPolicyRepository): BorrowBookPolicy {
        return BorrowBookPolicy(repository)
    }

    @Bean
    fun borrowBook(repository: BorrowBookRepository, policy: BorrowBookPolicy): BorrowBook {
        return BorrowBookExecutor(repository, policy)
    }

    @Bean
    fun extendLoanRepository(repository: BookEventStoreRepository): ExtendLoanRepository {
        return ExtendLoanEventStoreRepository(repository)
    }

    @Bean
    fun extendLoanPolicyRepository(repository: StudentEventStoreRepository): ExtendLoanPolicyRepository {
        return ExtendLoanPolicyEventStoreRepository(repository)
    }

    @Bean
    fun extendLoanPolicy(repository: ExtendLoanPolicyRepository): ExtendLoanPolicy {
        return ExtendLoanPolicy(repository)
    }

    @Bean
    fun extendLoan(repository: ExtendLoanRepository, policy: ExtendLoanPolicy): ExtendLoan {
        return ExtendLoanExecutor(repository, policy)
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