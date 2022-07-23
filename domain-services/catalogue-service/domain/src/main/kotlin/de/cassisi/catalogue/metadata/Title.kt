package de.cassisi.catalogue.metadata

data class Title(val title: String) {
    init {
        require(title.isNotBlank())
    }
}
