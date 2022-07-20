package de.cassisi.student.student.updatematriculationstatus

import de.cassisi.student.student.UpdateMatriculationStatusCommand

interface UpdateMatriculationStatus {

    fun execute(command: UpdateMatriculationStatusCommand)

}