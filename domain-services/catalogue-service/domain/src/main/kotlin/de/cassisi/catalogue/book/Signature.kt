package de.cassisi.catalogue.book

data class Signature(val signature: String) {

    init {
        require(signature.isNotBlank())
    }

}
