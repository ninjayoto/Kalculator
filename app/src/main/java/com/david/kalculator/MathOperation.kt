package com.david.calculator

object MathOperation {

    @Throws(ArithmeticException::class)
    fun addition(operand1: Double, operand2: Double): Double {
        val result = operand1 + operand2

        println("Addition result: " + result)

        if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result) || result == java.lang.Double.MAX_VALUE)
            throw ArithmeticException("result is not valid")

        return result
    }

    @Throws(ArithmeticException::class)
    fun subtraction(operand1: Double, operand2: Double): Double {
        val result = operand1 - operand2

        println("Subtraction result: " + result)

        if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result) || result == java.lang.Double.MAX_VALUE)
            throw ArithmeticException("result is not valid")

        return result
    }

    @Throws(ArithmeticException::class)
    fun multiplication(operand1: Double, operand2: Double): Double {
        val result = operand1 * operand2

        println("Multiplication result: " + result)

        if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result))
            throw ArithmeticException("result is not valid")

        return result
    }

    @Throws(ArithmeticException::class)
    fun division(dividend: Double, divisor: Double): Double {
        val result = dividend / divisor

        println("Division result: " + result)

        if (java.lang.Double.isNaN(result) || java.lang.Double.isInfinite(result))
            throw ArithmeticException("result is not valid")

        return result
    }

    @Throws(ArithmeticException::class)
    fun exponentiation(base: Double, exponent: Double): Double {
        val result = Math.pow(base, exponent)

        println("Exponentiation result: " + result)

        if (result == 0.0 || java.lang.Double.isNaN(result) || java.lang.Double.isInfinite(result)) {
            throw ArithmeticException("result is not valid")
        }

        return result
    }

    @Throws(ArithmeticException::class)
    fun squareRoot(radicand: Double): Double {
        val result = Math.sqrt(radicand)

        println("Square Root result: " + result)

        if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result)) {
            throw ArithmeticException("result is not valid")
        }

        return result
    }

    @Throws(ArithmeticException::class)
    fun logarithm10(value: Double): Double {
        val result = Math.log10(value)

        println("Logarithm10 result: " + result)

        if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result)) {
            throw ArithmeticException("result is not valid")
        }

        return result
    }

    @Throws(ArithmeticException::class)
    fun naturalLogarithm(value: Double): Double {
        val result = Math.log(value)

        println("Natural Logarithm result: " + result)

        if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result)) {
            throw ArithmeticException("result is not valid")
        }

        return result
    }

    @Throws(ArithmeticException::class)
    fun factorial(value: Double): Double {
        if (value < 0 || value % 1 != 0.0)
            throw ArithmeticException("result is not valid")

        var result = 1.0
        var factor = value

        while (factor != 0.0) {
            result *= factor
            factor--
        }

        return result
    }

    fun changeSign(number: Double): Double {
        return number * -1
    }
}
