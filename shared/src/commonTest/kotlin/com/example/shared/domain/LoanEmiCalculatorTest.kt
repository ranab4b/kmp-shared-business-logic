package com.example.shared.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Golden test cases for the EMI formula. These same (input -> expected output)
 * pairs are what the before/after demo apps are checked against for parity —
 * see kmp-adoption-before-after/docs/MIGRATION-GUIDE.md.
 */
class LoanEmiCalculatorTest {

    private data class Case(
        val principal: Double,
        val annualInterestRatePercent: Double,
        val tenureMonths: Int,
        val expectedEmi: Double,
        val expectedTotalPayment: Double,
        val expectedTotalInterest: Double
    )

    private val goldenCases = listOf(
        Case(100000.0, 10.5, 12, 8814.86, 105778.32, 5778.32),
        Case(500000.0, 8.5, 60, 10258.27, 615496.20, 115496.20),
        Case(1000000.0, 7.25, 240, 7903.76, 1896902.40, 896902.40),
        Case(250000.0, 12.0, 36, 8303.58, 298928.88, 48928.88),
        Case(50000.0, 0.0, 10, 5000.00, 50000.00, 0.00),
        Case(750000.0, 9.99, 180, 8054.95, 1449891.00, 699891.00),
        Case(1200000.0, 6.75, 84, 17964.92, 1509053.28, 309053.28)
    )

    @Test
    fun `matches golden EMI values for every table case`() {
        goldenCases.forEachIndexed { index, case ->
            val result = LoanEmiCalculator.calculate(
                LoanInput(case.principal, case.annualInterestRatePercent, case.tenureMonths)
            )
            assertEquals(case.expectedEmi, result.monthlyEmi, "case[$index] monthlyEmi")
            assertEquals(case.expectedTotalPayment, result.totalPayment, "case[$index] totalPayment")
            assertEquals(case.expectedTotalInterest, result.totalInterest, "case[$index] totalInterest")
        }
    }

    @Test
    fun `zero interest rate splits principal evenly`() {
        val result = LoanEmiCalculator.calculate(LoanInput(50000.0, 0.0, 10))
        assertEquals(5000.0, result.monthlyEmi)
        assertEquals(0.0, result.totalInterest)
    }

    @Test
    fun `rejects non positive principal`() {
        assertFailsWith<IllegalArgumentException> {
            LoanInput(0.0, 10.0, 12)
        }
    }

    @Test
    fun `rejects negative interest rate`() {
        assertFailsWith<IllegalArgumentException> {
            LoanInput(1000.0, -1.0, 12)
        }
    }

    @Test
    fun `rejects non positive tenure`() {
        assertFailsWith<IllegalArgumentException> {
            LoanInput(1000.0, 10.0, 0)
        }
    }
}
