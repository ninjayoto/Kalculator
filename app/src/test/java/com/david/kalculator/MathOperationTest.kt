package com.david.calculator

import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(JUnitParamsRunner::class)
class MathOperationTest {

    @Parameters(method = "getValidAdditionInput")
    @Test
    fun additionShouldReturnExpectedValuesWhenOperandsAreWithinLimits(
            operand1: Double, operand2: Double, expectedValue: Double) {
        assertEquals(expectedValue, MathOperation.addition(operand1, operand2), 0.0)
    }

    private fun getValidAdditionInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(1, 1, 2),
                arrayOf<Any>(-1, 1, 0),
                arrayOf<Any>(-1, -1, -2),
                arrayOf<Any>(3.2, 2.5, 5.7))
    }

    @Parameters(method = "getInvalidAdditionInput")
    @Test(expected = ArithmeticException::class)
    fun additionShouldThrowWhenResultOverflow(operand1: Double, operand2: Double) {
        MathOperation.addition(operand1, operand2)
    }

    private fun getInvalidAdditionInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(java.lang.Double.MAX_VALUE, 1),
                arrayOf<Any>(java.lang.Double.NaN, 1),
                arrayOf<Any>(java.lang.Double.POSITIVE_INFINITY, -1),
                arrayOf<Any>(java.lang.Double.NEGATIVE_INFINITY, 2.5))
    }

    @Parameters(method = "getValidSubtractionInput")
    @Test
    fun subtractionShouldReturnExpectedValueWhenOperandsAreWithinLimits(
            operand1: Double, operand2: Double, expectedValue: Double) {
        assertEquals(expectedValue, MathOperation.subtraction(operand1, operand2), 0.001)
    }

    private fun getValidSubtractionInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(1, 1, 0),
                arrayOf<Any>(-1, 1, -2),
                arrayOf<Any>(-1, -1, 0),
                arrayOf<Any>(3.2, 2.5, 0.7))
    }

    @Parameters(method = "getInvalidSubtractionInput")
    @Test(expected = ArithmeticException::class)
    fun subtractionShouldThrowWhenResultUnderflow(operand1: Double, operand2: Double) {
        MathOperation.subtraction(operand1, operand2)
    }

    private fun getInvalidSubtractionInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(java.lang.Double.MAX_VALUE, 7),
                arrayOf<Any>(1.1, java.lang.Double.NaN),
                arrayOf<Any>(-3, java.lang.Double.POSITIVE_INFINITY),
                arrayOf<Any>(java.lang.Double.NEGATIVE_INFINITY, 3.5))

    }

    @Parameters(method = "getValidMultiplicationInput")
    @Test
    fun multiplicationShouldReturnExpectedValueWhenOperandsAreWithinLimits(
            operand1: Double, operand2: Double, expectedValue: Double) {
        assertEquals(expectedValue, MathOperation.multiplication(operand1, operand2), 0.001)
    }

    private fun getValidMultiplicationInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(1, 1, 1),
                arrayOf<Any>(-1, 0, -0),
                arrayOf<Any>(-1, -1, 1),
                arrayOf<Any>(3.2, 2.5, 8),
                arrayOf<Any>(0.3, -3, -0.9))

    }

    @Parameters(method = "getInvalidMultiplicationInput")
    @Test(expected = ArithmeticException::class)
    fun multiplicationShouldThrowWhenResultOverflows(operand1: Double, operand2: Double) {
        MathOperation.multiplication(operand1, operand2)
    }

    private fun getInvalidMultiplicationInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(java.lang.Double.MAX_VALUE, 2),
                arrayOf<Any>(1.2, java.lang.Double.NaN),
                arrayOf<Any>(-7.4, java.lang.Double.POSITIVE_INFINITY),
                arrayOf<Any>(java.lang.Double.NEGATIVE_INFINITY, 3.5))

    }

    @Parameters(method = "getValidDivisionInput")
    @Test
    fun divisionShouldReturnExpectedValueWhenOperandsAreWithinLimits(
            dividend: Double, divisor: Double, expectedValue: Double) {
        assertEquals(expectedValue, MathOperation.division(dividend, divisor), 0.001)
    }

    private fun getValidDivisionInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(3, 2, 1.5),
                arrayOf<Any>(-4, 2, -2),
                arrayOf<Any>(33, 5, 6.6),
                arrayOf<Any>(25, 0.36, 69.44444),
                arrayOf<Any>(0.3, -0.25, -1.2))
    }

    @Parameters(method = "getInvalidDivisionInput")
    @Test(expected = ArithmeticException::class)
    fun divisionShouldThrowWhenInputIsInvalid(dividend: Double, divisor: Double) {
        MathOperation.division(dividend, divisor)
    }

    private fun getInvalidDivisionInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(java.lang.Double.MAX_VALUE, 0.7),
                arrayOf<Any>(1.2, java.lang.Double.NaN),
                arrayOf<Any>(java.lang.Double.NEGATIVE_INFINITY, 3.5),
                arrayOf<Any>(25, 0))
    }

    @Parameters(method = "getValidChangeSignInput")
    @Test
    fun changeSignShouldReturnExpectedValueValueWhenInputIsValid(
            operand: Double, expectedValue: Double) {
        assertThat(MathOperation.changeSign(operand)).isEqualTo(expectedValue)
    }

    private fun getValidChangeSignInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(3, -3),
                arrayOf<Any>(-4, 4),
                arrayOf<Any>(33, -33),
                arrayOf<Any>(-0.66, 0.66),
                arrayOf<Any>(0.3, -0.3))
    }

    @Parameters(method = "getValidExponentiationInput")
    @Test
    fun exponentiationShouldReturnExpectedValuesWhenInputIsValid(
            base: Double, exponent: Double, expectedValue: Double) {
        assertThat(MathOperation.exponentiation(base, exponent)).isEqualTo(expectedValue)
    }

    private fun getValidExponentiationInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(2, 0, 1),
                arrayOf<Any>(2, 1, 2),
                arrayOf<Any>(2, 3, 8),
                arrayOf<Any>(2, -2, 0.25)
        )
    }

    @Parameters(method = "getInvalidExponentiationInput")
    @Test(expected = ArithmeticException::class)
    fun exponentiationShouldThrowsWhenInputValuesAreInvalid(base: Double, exponent: Double) {
        MathOperation.exponentiation(base, exponent)
    }

    private fun getInvalidExponentiationInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(0, -2),
                arrayOf<Any>(2, java.lang.Double.POSITIVE_INFINITY),
                arrayOf<Any>(2, java.lang.Double.NEGATIVE_INFINITY),
                arrayOf<Any>(2, java.lang.Double.NaN))
    }

    @Parameters(method = "getValidSquareRootInput")
    @Test
    fun squareRootShouldReturnExpectedValuesWhenInputIsValid(radicand: Double, expectedValue: Double) {
        assertThat(MathOperation.squareRoot(radicand)).isEqualTo(expectedValue)
    }

    private fun getValidSquareRootInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(4, 2),
                arrayOf<Any>(49, 7),
                arrayOf<Any>(121, 11),
                arrayOf<Any>(25, 5))
    }

    @Parameters(method = "getInvalidSquareRootInput")
    @Test(expected = ArithmeticException::class)
    fun squareRootShouldThrowsWhenInputValuesAreInvalid(radicand: Double) {
        MathOperation.squareRoot(radicand)
    }

    private fun getInvalidSquareRootInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(-3),
                arrayOf<Any>(java.lang.Double.POSITIVE_INFINITY),
                arrayOf<Any>(-2.5),
                arrayOf<Any>(java.lang.Double.NaN))
    }

    @Parameters(method = "getValidLogarithm10Input")
    @Test
    fun logarithm10ShouldReturnExpectedValuesWhenInputIsValid(
            value: Double, expectedValue: Double) {
        assertEquals(expectedValue, MathOperation.logarithm10(value), 0.00001)
    }

    private fun getValidLogarithm10Input(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(4, 0.6020599913),
                arrayOf<Any>(49, 1.6901960800),
                arrayOf<Any>(121, 2.0827853703),
                arrayOf<Any>(25, 1.3979400086))
    }

    @Parameters(method = "getInvalidLogarithm10Input")
    @Test(expected = ArithmeticException::class)
    fun logarithm10ShouldThrowsWhenInputValuesAreInvalid(value: Double) {
        MathOperation.logarithm10(value)
    }

    private fun getInvalidLogarithm10Input(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(-3),
                arrayOf<Any>(java.lang.Double.POSITIVE_INFINITY),
                arrayOf<Any>(-2.5),
                arrayOf<Any>(java.lang.Double.NaN),
                arrayOf<Any>(0))
    }

    @Parameters(method = "getValidNaturalLogInput")
    @Test
    fun naturalLogarithmShouldReturnExpectedValuesWhenInputIsValid(
            value: Double, expectedValue: Double) {
        assertEquals(expectedValue, MathOperation.naturalLogarithm(value), 0.00001)
    }

    private fun getValidNaturalLogInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(4, 1.386294),
                arrayOf<Any>(49, 3.891820),
                arrayOf<Any>(121, 4.795790),
                arrayOf<Any>(25, 3.218875))
    }

    @Parameters(method = "getInvalidNaturalLogInput")
    @Test(expected = ArithmeticException::class)
    fun naturalLogarithmShouldThrowsWhenInputValuesAreInvalid(value: Double) {
        MathOperation.naturalLogarithm(value)
    }

    private fun getInvalidNaturalLogInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(-3),
                arrayOf<Any>(java.lang.Double.POSITIVE_INFINITY),
                arrayOf<Any>(-2.5),
                arrayOf<Any>(java.lang.Double.NaN),
                arrayOf<Any>(0))
    }

    @Parameters(method = "getValidFactorialInput")
    @Test
    fun factorialShouldReturnExpectedValuesWhenInputIsValid(
            value: Double, expectedValue: Double) {
        assertThat(MathOperation.factorial(value)).isEqualTo(expectedValue)
    }

    private fun getValidFactorialInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(4, 24),
                arrayOf<Any>(7, 5040),
                arrayOf<Any>(10, 3628800),
                arrayOf<Any>(0, 1))
    }

    @Parameters(method = "getInvalidFactorialInput")
    @Test(expected = ArithmeticException::class)
    fun factorialShouldThrowsWhenInputValuesAreInvalid(value: Double) {
        MathOperation.factorial(value)
    }

    private fun getInvalidFactorialInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>(-0.5),
                arrayOf<Any>(java.lang.Double.POSITIVE_INFINITY),
                arrayOf<Any>(-244.3),
                arrayOf<Any>(java.lang.Double.NaN),
                arrayOf<Any>(3.2))
    }
}