package de.cassisi.lending.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.student.StudentEventStoreRepository
import de.cassisi.lending.student.charge.UpdateStudentCharges
import de.cassisi.lending.student.charge.UpdateStudentChargesExecutor
import de.cassisi.lending.student.charge.UpdateStudentChargesRepository
import de.cassisi.lending.student.matriculation.UpdateMatriculationStatus
import de.cassisi.lending.student.matriculation.UpdateMatriculationStatusEventStoreRepository
import de.cassisi.lending.student.matriculation.UpdateMatriculationStatusExecutor
import de.cassisi.lending.student.matriculation.UpdateMatriculationStatusRepository
import de.cassisi.lending.student.register.RegisterStudent
import de.cassisi.lending.student.register.RegisterStudentEventStoreRepository
import de.cassisi.lending.student.register.RegisterStudentExecutor
import de.cassisi.lending.student.register.RegisterStudentRepository
import de.cassisi.lending.student.updatecharge.ChargeStudentEventStoreRepository
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
    fun chargeStudentRepository(repository: StudentEventStoreRepository): UpdateStudentChargesRepository {
        return ChargeStudentEventStoreRepository(repository)
    }

    @Bean
    fun chargeStudent(repository: UpdateStudentChargesRepository): UpdateStudentCharges {
        return UpdateStudentChargesExecutor(repository)
    }

    @Bean
    fun updateMatriculationRepository(repository: StudentEventStoreRepository): UpdateMatriculationStatusRepository {
        return UpdateMatriculationStatusEventStoreRepository(repository)
    }

    @Bean
    fun updateMatriculationStatus(repository: UpdateMatriculationStatusRepository): UpdateMatriculationStatus {
        return UpdateMatriculationStatusExecutor(repository)
    }

}