package de.cassisi.catalogue.metadata

data class Publisher(val name: String) {
    init {
        require(name.isNotBlank())
    }
}
