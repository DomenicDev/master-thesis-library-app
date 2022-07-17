package de.cassisi.catalogue.student

/**
 * Charges containing the amount of money
 */
data class Charges(val amount: Int) {

    init {
        if (amount < 0) throw IllegalArgumentException("Charges are not allowed to be negative")
    }

}
