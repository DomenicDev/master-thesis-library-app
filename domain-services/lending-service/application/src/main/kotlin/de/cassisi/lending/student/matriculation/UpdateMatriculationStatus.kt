package de.cassisi.lending.student.matriculation

import de.cassisi.lending.student.UpdateMatriculationStatusCommand

interface UpdateMatriculationStatus {

    fun execute(command: UpdateMatriculationStatusCommand)

}