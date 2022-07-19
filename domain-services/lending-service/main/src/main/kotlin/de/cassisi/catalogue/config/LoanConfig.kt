package de.cassisi.catalogue.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.loan.LoanCreationPolicy
import de.cassisi.lending.loan.LoanCreationPolicyRepository
import de.cassisi.lending.loan.LoanEventStoreRepository
import de.cassisi.lending.loan.close.CloseLoan
import de.cassisi.lending.loan.close.CloseLoanEventStoreRepository
import de.cassisi.lending.loan.close.CloseLoanExecutor
import de.cassisi.lending.loan.close.CloseLoanRepository
import de.cassisi.lending.loan.create.*
import de.cassisi.lending.loan.extend.ExtendLoan
import de.cassisi.lending.loan.extend.ExtendLoanEventStoreRepository
import de.cassisi.lending.loan.extend.ExtendLoanExecutor
import de.cassisi.lending.loan.extend.ExtendLoanRepository
import de.cassisi.lending.student.StudentEventStoreRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoanConfig {

    @Bean
    fun loanEventStoreRepository(eventStoreDBClient: EventStoreDBClient): LoanEventStoreRepository {
        return LoanEventStoreRepository(eventStoreDBClient)
    }

    @Bean
    fun createLoanRepository(repository: LoanEventStoreRepository): CreateLoanRepository {
        return CreateLoanEventStoreRepository(repository)
    }

    @Bean
    fun loanCreationPolicyRepository(repository: StudentEventStoreRepository): LoanCreationPolicyRepository {
        return LoanCreationPolicyEventStoreRepository(repository)
    }

    @Bean
    fun loanCreationPolicy(repository: LoanCreationPolicyRepository): LoanCreationPolicy {
        return LoanCreationPolicy(repository)
    }

    @Bean
    fun createLoan(repository: CreateLoanRepository, policy: LoanCreationPolicy): CreateLoan {
        return CreateLoanExecutor(repository, policy)
    }

    @Bean
    fun closeLoanRepository(repository: LoanEventStoreRepository): CloseLoanRepository {
        return CloseLoanEventStoreRepository(repository)
    }

    @Bean
    fun closeLoan(repository: CloseLoanRepository): CloseLoan {
        return CloseLoanExecutor(repository)
    }

    @Bean
    fun extendLoanRepository(repository: LoanEventStoreRepository): ExtendLoanRepository {
        return ExtendLoanEventStoreRepository(repository)
    }

    @Bean
    fun extendLoan(repository: ExtendLoanRepository): ExtendLoan {
        return ExtendLoanExecutor(repository)
    }

}