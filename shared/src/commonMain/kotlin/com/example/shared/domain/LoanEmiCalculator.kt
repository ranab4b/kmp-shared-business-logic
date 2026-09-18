package com.example.shared.domain

import kotlin.math.floor
import kotlin.math.pow

/**
 * Pure domain-layer EMI (equated monthly installment) calculator.
 *
 * No Android or iOS framework imports live here — this is the one piece of
 * business logic that both the Android and iOS apps consume identically.
 * Currency/date formatting stays native on each platform; only the math is shared.
 */
object LoanEmiCalculator {

    fun calculate(input: LoanInput): LoanResult {
        val emi = if (input.annualInterestRatePercent == 0.0) {
            input.principal / input.tenureMonths
        } else {
            val monthlyRate = input.annualInterestRatePercent / 100.0 / 12.0
            val factor = (1.0 + monthlyRate).pow(input.tenureMonths)
            input.principal * monthlyRate * factor / (factor - 1.0)
        }

        val roundedEmi = roundToCents(emi)
        val totalPayment = roundToCents(roundedEmi * input.tenureMonths)
        val totalInterest = roundToCents(totalPayment - input.principal)

        return LoanResult(
            monthlyEmi = roundedEmi,
            totalPayment = totalPayment,
            totalInterest = totalInterest
        )
    }

    private fun roundToCents(value: Double): Double = floor(value * 100.0 + 0.5) / 100.0
}
