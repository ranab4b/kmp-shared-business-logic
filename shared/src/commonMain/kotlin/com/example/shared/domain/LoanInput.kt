package com.example.shared.domain

/**
 * Inputs for an equated monthly installment (EMI) calculation.
 *
 * @property principal loan amount, in the currency's smallest whole unit (e.g. dollars, not cents)
 * @property annualInterestRatePercent nominal annual interest rate, e.g. 10.5 for 10.5%
 * @property tenureMonths loan duration in months
 */
data class LoanInput(
    val principal: Double,
    val annualInterestRatePercent: Double,
    val tenureMonths: Int
) {
    init {
        require(principal > 0) { "principal must be > 0, was $principal" }
        require(annualInterestRatePercent >= 0) { "annualInterestRatePercent must be >= 0, was $annualInterestRatePercent" }
        require(tenureMonths > 0) { "tenureMonths must be > 0, was $tenureMonths" }
    }
}
