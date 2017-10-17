package com.david.calculator

import org.junit.Test
import org.junit.runner.RunWith

import junitparams.JUnitParamsRunner
import junitparams.Parameters

import com.google.common.truth.Truth.assertThat

import com.david.calculator.MathExpression.addCharacter
import com.david.calculator.MathExpression.removeLastCharacter

@RunWith(JUnitParamsRunner::class)
class MathExpressionTest {

    @Parameters(method = "getValidAddCharacterInput")
    @Test
    fun addCharacterShouldReturnExpectedExpressionWhenInputIsValid(
            expression: String, character: String, expectedValue: String) {
        assertThat(expression.addCharacter(character)).isEqualTo(expectedValue)
    }

    private fun getValidAddCharacterInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>("", "1", "1"),
                arrayOf<Any>("12+", "2", "12+2"),
                arrayOf<Any>("23/4", "+", "23/4+"),
                arrayOf<Any>("(2/3", "-", "(2/3-"),
                arrayOf<Any>("3.4f(", "5", "3.4fact(5"),
                arrayOf<Any>("2l(", "5", "2log(5"),
                arrayOf<Any>("2(n(3", "2", "2(ln(32"),
                arrayOf<Any>("35r(7", "+", "35sqrt(7+"),
                arrayOf<Any>("55", "/", "55/"),
                arrayOf<Any>("3", ".", "3."),
                arrayOf<Any>("3.5", "(", "3.5("),
                arrayOf<Any>("3.5(4*5", ")", "3.5(4*5)"),
                arrayOf<Any>("3", "^", "3^"))
    }

    @Parameters(method = "getInvalidAddCharacterInput")
    @Test(expected = IllegalArgumentException::class)
    fun addCharacterShouldThrowWhenInputIsInvalid(character: String) {
        "this is a String".addCharacter(character)
    }

    private fun getInvalidAddCharacterInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>("a"),
                arrayOf<Any>("?"),
                arrayOf<Any>(","),
                arrayOf<Any>("<"),
                arrayOf<Any>(":"),
                arrayOf<Any>("z"))
    }

    @Parameters(method = "getOperatorCharacters")
    @Test
    fun addCharacterShouldReplaceOperatorIfLasCharacterIsAlreadyAnOperator(
            expression: String, character: String, expectedExpression: String) {
        assertThat(expression.addCharacter(character)).isEqualTo(expectedExpression)
    }

    private fun getOperatorCharacters(): Array<Any> {
        return arrayOf(
                arrayOf<Any>("12+", "-", "12-"),
                arrayOf<Any>("1+", "*", "1*"),
                arrayOf<Any>("-21+", "/", "-21/"),
                arrayOf<Any>("2-", "+", "2+"),
                arrayOf<Any>("-", "+", "+"),
                arrayOf<Any>("222-", "^", "222^"),
                arrayOf<Any>("3r", "*", "3*"),
                arrayOf<Any>("*", "r", "sqrt"),
                arrayOf<Any>("3.4*", "f", "3.4fact"),
                arrayOf<Any>("33l", "n", "33ln"))
    }


    @Parameters(method = "getValidRemoveLastCharacterInput")
    @Test
    fun removeLastCharacterShouldReturnExpectedExpression(expression: String, expectedExpression: String) {
        assertThat(expression.removeLastCharacter()).isEqualTo(expectedExpression)
    }

    private fun getValidRemoveLastCharacterInput(): Array<Any> {
        return arrayOf(
                arrayOf<Any>("1", ""),
                arrayOf<Any>("12+", "12"),
                arrayOf<Any>("23/4", "23/"),
                arrayOf<Any>("(2/3", "(2/"),
                arrayOf<Any>("3.4f(", "3.4fact"),
                arrayOf<Any>("2l(", "2log"),
                arrayOf<Any>("2(n(3", "2(ln("),
                arrayOf<Any>("35r(7", "35sqrt("),
                arrayOf<Any>("55", "5"),
                arrayOf<Any>("3log", "3"),
                arrayOf<Any>("3.5", "3."),
                arrayOf<Any>("2sqrt", "2"),
                arrayOf<Any>("3^", "3"),
                arrayOf<Any>("", ""))
    }
}