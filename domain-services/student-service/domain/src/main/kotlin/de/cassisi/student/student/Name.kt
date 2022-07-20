package de.cassisi.student.student

data class Name(val firstname: String, val lastName: String) {
    init {
        if (firstname.isBlank() || lastName.isBlank()) {
            throw IllegalArgumentException("firstname and lastname must not be empty")
        }
    }
}

