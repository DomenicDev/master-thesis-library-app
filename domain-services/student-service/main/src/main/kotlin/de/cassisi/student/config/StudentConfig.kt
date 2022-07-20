package de.cassisi.student.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.student.student.StudentEventStoreRepository
import de.cassisi.student.student.register.RegisterStudent
import de.cassisi.student.student.register.RegisterStudentEventStoreRepository
import de.cassisi.student.student.register.RegisterStudentExecutor
import de.cassisi.student.student.register.RegisterStudentRepository
import de.cassisi.student.student.updatematriculationstatus.UpdateMatriculationStatus
import de.cassisi.student.student.updatematriculationstatus.UpdateMatriculationStatusEventStoreRepository
import de.cassisi.student.student.updatematriculationstatus.UpdateMatriculationStatusExecutor
import de.cassisi.student.student.updatematriculationstatus.UpdateMatriculationStatusRepository
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
    fun updateMatriculationStatusRepository(repository: StudentEventStoreRepository): UpdateMatriculationStatusRepository {
        return UpdateMatriculationStatusEventStoreRepository(repository)
    }

    @Bean
    fun updateMatriculationStatus(repository: UpdateMatriculationStatusRepository): UpdateMatriculationStatus {
        return UpdateMatriculationStatusExecutor(repository)
    }
}