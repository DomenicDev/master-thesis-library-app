package de.cassisi.catalogue.metadata

data class ISBN(val isbn: String) {
    init {
        require(isbn.isNotBlank())
    }
}
