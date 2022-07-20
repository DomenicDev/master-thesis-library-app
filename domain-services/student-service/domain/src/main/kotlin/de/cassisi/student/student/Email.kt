package de.cassisi.student.student

data class Email(val email: String) {
    init {
        if (email.isBlank() || !email.contains("@")) {
            throw IllegalArgumentException("email is empty or not in the right format")
        }
    }
}
