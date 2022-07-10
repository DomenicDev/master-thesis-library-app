package de.cassisi.catalogue.common

data class Version(val version: Long) {

    companion object {
        fun init() = Version(-1)
    }

}