package de.cassisi.student.student

data class MatriculationNumber(val matriculationNumber: Int) {

    init {
        if (matriculationNumber < 0) {
            throw IllegalArgumentException("Matriculation number must not be negative")
        }
    }
}
