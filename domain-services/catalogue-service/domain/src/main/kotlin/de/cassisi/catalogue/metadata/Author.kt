package de.cassisi.catalogue.metadata

data class Author(val fullName: String) {
    init {
        require(fullName.isNotBlank())
    }
}
