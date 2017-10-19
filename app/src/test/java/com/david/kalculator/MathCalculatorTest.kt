package com.david.kalculator

import org.junit.Test
import org.junit.runner.RunWith

import junitparams.JUnitParamsRunner
import junitparams.Parameters

import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals

import com.david.kalculator.MathCalculator.splitExpression

@RunWith(JUnitParamsRunner::class)
class MathCalculatorTest {

    @Parameters(method = "getValidSplitExpressionInput")
    @Test
    fun splitExpressionShouldReturnExpectedArray(expression: String, expectedArray: Array<String>) {
        assertThat<String>(expression.splitExpression()).asList().containsExactly(*expectedArray)
    }

    private fun getValidSplitExpressionInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>("1", arrayOf("1")),
                arrayOf<Any>("12+", arrayOf("12", "+")),
                arrayOf<Any>("3.4/5", arrayOf("3.4", "/", "5")),
                arrayOf<Any>("0.5r(5+3", arrayOf("0.5", "r", "8.0")),
                arrayOf<Any>("3f(", arrayOf("3", "f")),
                arrayOf<Any>("(4*3", arrayOf("12.0")),
                arrayOf<Any>("(4+2)(7*3)", arrayOf("6.0", "*", "21.0")))

    }

    @Parameters(method = "getValidExpression")
    @Test
    fun calculateShouldReturnExpectedValueWhenExpressionIsValid(expression: String, expectedValue: Double) {
        assertEquals(expectedValue, MathCalculator.calculate(expression), 0.001)
    }

    private fun getValidExpression(): Array<Any> {
        return arrayOf(
                arrayOf<Any>("10+24*", 34),
                arrayOf<Any>("2+3-5+12*3.4/12-34+15", -15.6),
                arrayOf<Any>("12-12*5-10*5+35", -15),
                arrayOf<Any>("2*2*2*2/2.33/1.45+23+", 27.73582951),
                arrayOf<Any>("3+2+(5", 10),
                arrayOf<Any>("3+2+(5/5", 6),
                arrayOf<Any>("3+2+(5/5*(3", 8),
                arrayOf<Any>("3+2+(5/5*(3+2", 10),
                arrayOf<Any>("3+2+(5/5*(3+2))*3", 30),
                arrayOf<Any>("3+2+((3*3)/(3*3))", 6),
                arrayOf<Any>("3+2(3*3)", 45),
                arrayOf<Any>("(3+", 3),
                arrayOf<Any>("(((", 0),
                arrayOf<Any>("7(-7", -49),
                arrayOf<Any>("2(3+2)4", 40),
                arrayOf<Any>("2(3+2)4^3", 64000),
                arrayOf<Any>("2(3+2)(4^3)", 640),
                arrayOf<Any>("(2(3+2)4)^3", 64000),
                arrayOf<Any>("2(r(42+7", 14),
                arrayOf<Any>("2(r(", 2),
                arrayOf<Any>("r(", 0),
                arrayOf<Any>("r(25", 5),
                arrayOf<Any>("f(", 0),
                arrayOf<Any>("f(3", 6),
                arrayOf<Any>("f(3+2)", 120),
                arrayOf<Any>("l(1", 0),
                arrayOf<Any>("n(10", 2.302585),
                arrayOf<Any>("l(5.6", 0.748188))
    }
}