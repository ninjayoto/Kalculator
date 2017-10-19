package com.david.kalculator

import com.david.kalculator.MathSymbols.ADDITION
import com.david.kalculator.MathSymbols.BASE_10_LOGARITHM
import com.david.kalculator.MathSymbols.DIVISION
import com.david.kalculator.MathSymbols.EMPTY_STRING
import com.david.kalculator.MathSymbols.EXPONENTIATION
import com.david.kalculator.MathSymbols.FACTORIAL
import com.david.kalculator.MathSymbols.MULTIPLICATION
import com.david.kalculator.MathSymbols.NATURAL_LOGARITHM
import com.david.kalculator.MathSymbols.NONE
import com.david.kalculator.MathSymbols.PARENTHESIS_END
import com.david.kalculator.MathSymbols.PARENTHESIS_START
import com.david.kalculator.MathSymbols.SQUARE_ROOT
import com.david.kalculator.MathSymbols.SUBTRACTION
import com.david.kalculator.MathExpression.adaptFromScreen
import com.david.kalculator.MathExpression.isABinaryOperator
import com.david.kalculator.MathExpression.isANumber
import com.david.kalculator.MathExpression.isAnUnaryOperator
import com.david.kalculator.MathExpression.isAnOperator

object MathCalculator {

    @Throws(ArithmeticException::class, NumberFormatException::class)
    fun calculate(expression: String): Double {
        var result = 0.0
        var unaryOperator = NONE
        var binaryOperator = NONE

        val elements = expression.adaptFromScreen().splitExpression()

        for (element in elements) {
            if (element.isAnUnaryOperator()) {
                unaryOperator = element

            } else if (element.isABinaryOperator()) {
                binaryOperator = element

            } else if (element.isANumber()) {
                if (unaryOperator == NONE) {
                    result = calculateBinaryOperation(result, java.lang.Double.parseDouble(element), binaryOperator)
                    binaryOperator = NONE

                } else {
                    result = calculateUnaryOperation(result, java.lang.Double.parseDouble(element), unaryOperator)
                    unaryOperator = NONE
                }
            }
        }

        return result
    }

    @Throws(IllegalArgumentException::class)
    fun String.splitExpression(): Array<String> {
        var expression = this

        while (expression.containsParenthesis()) {
            expression = expression.resolveParenthesis()
        }

        return expression.split("(?<=[flnr+*/^])|(?=[-flnr+*/^])".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    private fun String.containsParenthesis(): Boolean {
        return this.contains(PARENTHESIS_START)
    }

    private fun String.resolveParenthesis(): String {
        val parenthesisExpression = getRightmostParenthesis()
        val expressionWithoutParenthesis = parenthesisExpression.removeParenthesis()
        var resolvedExpression = expressionWithoutParenthesis.resolveExpression()
        resolvedExpression = resolvedExpression.addOperatorsIfNotExist(this, parenthesisExpression)

        return StringBuilder(this)
                .replace(this.getParenthesisStartIndex(), this.getParenthesisEndIndex(), resolvedExpression)
                .toString()
    }

    private fun String.getRightmostParenthesis(): String {
        val START_INDEX = this.getParenthesisStartIndex()
        val END_INDEX = this.getParenthesisEndIndex()

        return this.substring(START_INDEX, END_INDEX)
    }

    private fun String.getParenthesisStartIndex(): Int {
        return this.lastIndexOf(PARENTHESIS_START)
    }

    private fun String.getParenthesisEndIndex(): Int {
        val index = this.indexOf(PARENTHESIS_END, this.getParenthesisStartIndex())

        return if (index == -1) {
            this.length
        } else index + 1

    }

    private fun String.removeParenthesis(): String {
        return this.replace(PARENTHESIS_START, EMPTY_STRING)
                .replace(PARENTHESIS_END, EMPTY_STRING)
    }

    private fun String.resolveExpression(): String {
        return if (this.isEmpty() || this.matches("\\D?".toRegex())) EMPTY_STRING else calculate(this).toString()
    }

    private fun String.addOperatorsIfNotExist(expression: String, parenthesisExpression: String): String {
        var resolvedExpression = this

        if (parenthesisExpression.contains(PARENTHESIS_START))
            resolvedExpression = expression.addOperatorBeforeParenthesis(resolvedExpression)

        if (parenthesisExpression.contains(PARENTHESIS_END))
            resolvedExpression = expression.addOperatorAfterParenthesis(resolvedExpression)

        return resolvedExpression
    }

    private fun String.addOperatorBeforeParenthesis(resolvedExpression: String): String {
        try {
            val previousCharacter = this[this.getParenthesisStartIndex() - 1].toString()

            if (!previousCharacter.isAnOperator() && previousCharacter != PARENTHESIS_START)
                return MULTIPLICATION.plus(resolvedExpression)

        } catch (exception: IndexOutOfBoundsException) {
            return resolvedExpression
        }

        return resolvedExpression
    }

    private fun String.addOperatorAfterParenthesis(resolvedExpression: String): String {
        try {
            val nextCharacter = this[this.getParenthesisEndIndex()].toString()

            if (!nextCharacter.isAnOperator() && nextCharacter != PARENTHESIS_END)
                return resolvedExpression.plus(MULTIPLICATION)

        } catch (exception: IndexOutOfBoundsException) {
            return resolvedExpression
        }

        return resolvedExpression
    }

    private fun calculateUnaryOperation(accumulated: Double, operand: Double, operator: String): Double {
        var result = 0.0

        when (operator) {
            SQUARE_ROOT -> result = MathOperation.squareRoot(operand)
            BASE_10_LOGARITHM -> result = MathOperation.logarithm10(operand)
            NATURAL_LOGARITHM -> result = MathOperation.naturalLogarithm(operand)
            FACTORIAL -> result = MathOperation.factorial(operand)
        }
        return if (accumulated == 0.0) result else MathOperation.multiplication(accumulated, result)
    }

    private fun calculateBinaryOperation(operand1: Double, operand2: Double, operator: String): Double {
        when (operator) {
            ADDITION -> return MathOperation.addition(operand1, operand2)
            SUBTRACTION -> return MathOperation.subtraction(operand1, operand2)
            MULTIPLICATION -> return MathOperation.multiplication(operand1, operand2)
            DIVISION -> return MathOperation.division(operand1, operand2)
            EXPONENTIATION -> return MathOperation.exponentiation(operand1, operand2)
            else -> return MathOperation.addition(operand1, operand2)
        }
    }
}