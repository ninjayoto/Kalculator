package com.david.kalculator

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(MathCalculatorTest::class, MathExpressionTest::class, MathOperationTest::class)
class MathTestSuite
