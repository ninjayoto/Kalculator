package com.david.kalculator


object MathExpression {

    @Throws(IllegalArgumentException::class)
    fun String.addCharacter(character: String): String {

        throwsIfCharacterIsInvalid(character)

        return if (character.isAnOperator() && this.adaptFromScreen().endsWithOperator()) {
            this.replaceLastCharacter(character).adaptToScreen()

        } else this.adaptFromScreen().plus(character).adaptToScreen();
    }

    @Throws(IllegalArgumentException::class)
    private fun throwsIfCharacterIsInvalid(expression: String) {
        for (character in expression) {
            if (character.isInvalid()) {
                throw IllegalArgumentException(String.format("character %s is invalid", character))
            }
        }
    }

    fun String.adaptFromScreen(): String {
        return this.replace(MathSymbols.SQUARE_ROOT_SCREEN, MathSymbols.SQUARE_ROOT)
                .replace(MathSymbols.BASE_10_LOGARITHM_SCREEN, MathSymbols.BASE_10_LOGARITHM)
                .replace(MathSymbols.NATURAL_LOGARITHM_SCREEN, MathSymbols.NATURAL_LOGARITHM)
                .replace(MathSymbols.FACTORIAL_SCREEN, MathSymbols.FACTORIAL)
    }

    fun String.adaptToScreen(): String {
        return this.replace(MathSymbols.SQUARE_ROOT, MathSymbols.SQUARE_ROOT_SCREEN)
                .replace(MathSymbols.BASE_10_LOGARITHM, MathSymbols.BASE_10_LOGARITHM_SCREEN)
                .replace(MathSymbols.NATURAL_LOGARITHM, MathSymbols.NATURAL_LOGARITHM_SCREEN)
                .replace(MathSymbols.FACTORIAL, MathSymbols.FACTORIAL_SCREEN)
    }

    fun String.isAnOperator(): Boolean {
        return this.isABinaryOperator() || this.isAnUnaryOperator()
    }

    fun String.isABinaryOperator(): Boolean {
        return this == MathSymbols.ADDITION ||
                this == MathSymbols.SUBTRACTION ||
                this == MathSymbols.MULTIPLICATION ||
                this == MathSymbols.DIVISION ||
                this == MathSymbols.EXPONENTIATION
    }

    fun String.isAnUnaryOperator(): Boolean {
        return this == MathSymbols.SQUARE_ROOT ||
                this == MathSymbols.BASE_10_LOGARITHM ||
                this == MathSymbols.NATURAL_LOGARITHM ||
                this == MathSymbols.FACTORIAL
    }

    fun String.isANumber(): Boolean {
        return this.matches("-?\\d+(\\.\\d*)?".toRegex())
    }

    private fun Char.isInvalid(): Boolean {
        return !this.toString().matches("([0-9]|[-+*/]|[.]|[()^]|[flnr])".toRegex())
    }

    private fun String.endsWithOperator(): Boolean {
        return this.endsWith(MathSymbols.ADDITION) || this.endsWith(MathSymbols.SUBTRACTION) ||
                this.endsWith(MathSymbols.MULTIPLICATION) || this.endsWith(MathSymbols.DIVISION) ||
                this.endsWith(MathSymbols.EXPONENTIATION) || this.endsWith(MathSymbols.SQUARE_ROOT) ||
                this.endsWith(MathSymbols.NATURAL_LOGARITHM) || this.endsWith(MathSymbols.BASE_10_LOGARITHM) ||
                this.endsWith(MathSymbols.FACTORIAL) || this.endsWith(MathSymbols.DOT)
    }

    private fun String.replaceLastCharacter(character: String): String {
        val START_INDEX = 0
        val END_INDEX = this.length - 1

        if (this.length > 1) {
            val expression = this.substring(START_INDEX, END_INDEX)
            return expression.plus(character)
        }

        return character
    }

    fun String.removeLastCharacter(): String {
        val expression = this.adaptFromScreen()

        val START_INDEX = 0
        val END_INDEX = expression.length - 1

        return if (expression.isEmpty()) expression else expression.substring(START_INDEX, END_INDEX).adaptToScreen()

    }
}
