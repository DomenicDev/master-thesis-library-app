package de.cassisi.charging.student

data class Charges(val charges: Int) {

    fun add(toAdd: Int): Charges {
        val newCharges = this.charges+toAdd
        return Charges(newCharges)
    }

}
