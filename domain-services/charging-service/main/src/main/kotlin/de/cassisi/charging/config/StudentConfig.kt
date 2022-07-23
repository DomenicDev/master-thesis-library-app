package de.cassisi.charging.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.charging.student.StudentEventStoreRepository
import de.cassisi.charging.student.charge.ChargeStudent
import de.cassisi.charging.student.charge.ChargeStudentEventStoreRepository
import de.cassisi.charging.student.charge.ChargeStudentExecutor
import de.cassisi.charging.student.charge.ChargeStudentRepository
import de.cassisi.charging.student.clear.ClearCharges
import de.cassisi.charging.student.clear.ClearChargesEventStoreRepository
import de.cassisi.charging.student.clear.ClearChargesExecutor
import de.cassisi.charging.student.clear.ClearChargesRepository
import de.cassisi.charging.student.register.CreateChargeAccount
import de.cassisi.charging.student.register.CreateChargeAccountExecutor
import de.cassisi.charging.student.register.CreateChargeAccountRepository
import de.cassisi.charging.student.register.RegisterStudentEventStoreRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StudentConfig {

    @Bean
    fun studentEventStoreRepository(eventStoreDBClient: EventStoreDBClient): StudentEventStoreRepository {
        return StudentEventStoreRepository(eventStoreDBClient)
    }

    @Bean
    fun registerStudentRepository(repository: StudentEventStoreRepository): CreateChargeAccountRepository {
        return RegisterStudentEventStoreRepository(repository)
    }

    @Bean
    fun registerStudent(repository: CreateChargeAccountRepository): CreateChargeAccount {
        return CreateChargeAccountExecutor(repository)
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
    fun clearChargesRepository(repository: StudentEventStoreRepository): ClearChargesRepository {
        return ClearChargesEventStoreRepository(repository)
    }

    @Bean
    fun clearCharges(repository: ClearChargesRepository): ClearCharges {
        return ClearChargesExecutor(repository)
    }

}