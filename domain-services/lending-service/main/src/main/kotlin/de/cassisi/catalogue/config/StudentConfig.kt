package de.cassisi.catalogue.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.student.StudentEventStoreRepository
import de.cassisi.lending.student.charge.ChargeStudent
import de.cassisi.lending.student.charge.ChargeStudentEventStoreRepository
import de.cassisi.lending.student.charge.ChargeStudentExecutor
import de.cassisi.lending.student.charge.ChargeStudentRepository
import de.cassisi.lending.student.register.RegisterStudent
import de.cassisi.lending.student.register.RegisterStudentEventStoreRepository
import de.cassisi.lending.student.register.RegisterStudentExecutor
import de.cassisi.lending.student.register.RegisterStudentRepository
import de.cassisi.lending.student.resetcharge.ResetChargeEventStoreRepository
import de.cassisi.lending.student.resetcharge.ResetCharges
import de.cassisi.lending.student.resetcharge.ResetChargesExecutor
import de.cassisi.lending.student.resetcharge.ResetChargesRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StudentConfig {

    @Bean
    fun studentEventStoreRepository(eventStoreDBClient: EventStoreDBClient): StudentEventStoreRepository {
        return StudentEventStoreRepository(eventStoreDBClient)
    }

    @Bean
    fun registerStudentRepository(repository: StudentEventStoreRepository): RegisterStudentRepository {
        return RegisterStudentEventStoreRepository(repository)
    }

    @Bean
    fun registerStudent(repository: RegisterStudentRepository): RegisterStudent {
        return RegisterStudentExecutor(repository)
    }

    @Bean
    fun chargeStudentRepository(repository: StudentEventStoreRepository): ChargeStudentRepository {
        return ChargeStudentEventStoreRepository(repository)
    }

    @Bean
    fun chargeStudent(repository: ChargeStudentRepository): ChargeStudent {
        return ChargeStudentExecutor(repository)
    }

    @Bean
    fun resetChargeRepository(repository: StudentEventStoreRepository): ResetChargesRepository {
        return ResetChargeEventStoreRepository(repository)
    }

    @Bean
    fun resetCharges(repository: ResetChargesRepository): ResetCharges {
        return ResetChargesExecutor(repository)
    }

}