package com.example.shared.domain

/**
 * Result of an EMI calculation. All amounts are rounded to 2 decimal places
 * (the smallest currency subunit) so both platforms render identical figures.
 */
data class LoanResult(
    val monthlyEmi: Double,
    val totalPayment: Double,
    val totalInterest: Double
)
