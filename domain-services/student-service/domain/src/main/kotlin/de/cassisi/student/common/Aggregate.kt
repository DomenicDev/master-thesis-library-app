package de.cassisi.student.common

interface Aggregate<ID> {

    fun getId(): ID

    fun getVersion(): Version

}