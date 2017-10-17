package com.david.kalculator

import android.animation.Animator
import android.annotation.TargetApi
import java.text.NumberFormat
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextSwitcher
import android.widget.TextView

import java.util.Arrays
import java.util.Locale

import com.david.kalculator.MathExpression.removeLastCharacter
import com.david.kalculator.MathExpression.addCharacter

class HomeActivity : AppCompatActivity(), TextWatcher, Animator.AnimatorListener {

    private var resultScreen: LinearLayout? = null
    private var operations: TextView? = null
    private var result: TextSwitcher? = null
    private var clearButton: Button? = null
    private var parenthesisStartButton: Button? = null
    private var parenthesisEndButton: Button? = null
    private var equalButton: Button? = null
    private var additionButton: Button? = null
    private var subtractionButton: Button? = null
    private var multiplicationButton: Button? = null
    private var divisionButton: Button? = null
    private var exponentiationButton: Button? = null
    private var squareRootButton: Button? = null
    private var logarithm10Button: Button? = null
    private var naturalLogarithmButton: Button? = null
    private var factorialButton: Button? = null
    private var zeroButton: Button? = null
    private var doubleZeroButton: Button? = null
    private var oneButton: Button? = null
    private var twoButton: Button? = null
    private var threeButton: Button? = null
    private var fourButton: Button? = null
    private var fiveButton: Button? = null
    private var sixButton: Button? = null
    private var sevenButton: Button? = null
    private var eightButton: Button? = null
    private var nineButton: Button? = null
    private var dotButton: Button? = null
    private var buttons: List<Button>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initializeViewComponents()
        initializeViewListeners()
    }

    private fun initializeViewComponents() {
        resultScreen = findViewById<View>(R.id.result_screen) as LinearLayout
        operations = findViewById<View>(R.id.operations) as TextView
        result = findViewById<View>(R.id.result) as TextSwitcher
        clearButton = findViewById<View>(R.id.bt_clear) as Button
        parenthesisStartButton = findViewById<View>(R.id.bt_parenthesis_start) as Button
        parenthesisEndButton = findViewById<View>(R.id.bt_parenthesis_end) as Button
        equalButton = findViewById<View>(R.id.bt_equal) as Button
        additionButton = findViewById<View>(R.id.bt_addition) as Button
        subtractionButton = findViewById<View>(R.id.bt_subtraction) as Button
        multiplicationButton = findViewById<View>(R.id.bt_multiplication) as Button
        divisionButton = findViewById<View>(R.id.bt_division) as Button
        exponentiationButton = findViewById<View>(R.id.bt_exponentiation) as Button
        squareRootButton = findViewById<View>(R.id.bt_square_root) as Button
        logarithm10Button = findViewById<View>(R.id.bt_logarithm10) as Button
        naturalLogarithmButton = findViewById<View>(R.id.bt_natural_logarithm) as Button
        factorialButton = findViewById<View>(R.id.bt_factorial) as Button
        zeroButton = findViewById<View>(R.id.bt0) as Button
        doubleZeroButton = findViewById<View>(R.id.bt00) as Button
        oneButton = findViewById<View>(R.id.bt1) as Button
        twoButton = findViewById<View>(R.id.bt2) as Button
        threeButton = findViewById<View>(R.id.bt3) as Button
        fourButton = findViewById<View>(R.id.bt4) as Button
        fiveButton = findViewById<View>(R.id.bt5) as Button
        sixButton = findViewById<View>(R.id.bt6) as Button
        sevenButton = findViewById<View>(R.id.bt7) as Button
        eightButton = findViewById<View>(R.id.bt8) as Button
        nineButton = findViewById<View>(R.id.bt9) as Button
        dotButton = findViewById<View>(R.id.bt_dot) as Button

        buttons = Arrays.asList<Button>(parenthesisStartButton, parenthesisEndButton, additionButton,
                subtractionButton, multiplicationButton, divisionButton, exponentiationButton, squareRootButton,
                logarithm10Button, naturalLogarithmButton, factorialButton, zeroButton, doubleZeroButton,
                oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton,
                eightButton, nineButton, dotButton)
    }

    private fun initializeViewListeners() {
        operations!!.addTextChangedListener(this)

        result!!.setFactory {
            val resultView = TextView(this@HomeActivity)
            resultView.gravity = Gravity.CENTER
            resultView.textSize = 30f
            resultView
        }

        result!!.setInAnimation(this, R.anim.result_anim)

        clearButton!!.setOnClickListener {
            val expression = operations!!.text.toString()
            operations!!.setText(expression.removeLastCharacter())
        }

        clearButton!!.setOnLongClickListener {
            circularRevealCard(resultScreen)
            true
        }

        equalButton!!.setOnClickListener {
            val resultView = result!!.currentView as TextView
            resultView.setTextColor(ContextCompat.getColor(this@HomeActivity, R.color.colorAccent))
            result!!.setText(resultView.text.toString())
        }

        for (button in buttons!!) {
            button.setOnClickListener {
                val expression = operations!!.text.toString()
                operations!!.setText(expression.addCharacter(button.tag.toString()))
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun circularRevealCard(view: View?) {
        val finalRadius = Math.max(view!!.width, view.height).toFloat()

        val circularReveal = ViewAnimationUtils.createCircularReveal(view, 0, view.height, 0f, finalRadius * 1.5f)
        circularReveal.duration = 600
        circularReveal.addListener(this)

        circularReveal.start()
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        val expression = s.toString()

        if (!expression.isEmpty()) {
            try {
                (result!!.currentView as TextView).setTextColor(ContextCompat.getColor(this, R.color.secondaryText))
                result!!.setCurrentText(getFormattedNumber(MathCalculator.calculate(expression)))

            } catch (exception: IllegalArgumentException) {
                (result!!.currentView as TextView).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                result!!.setCurrentText(getString(R.string.wrong_expression))

            } catch (exception: ArithmeticException) {
                (result!!.currentView as TextView).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                result!!.setCurrentText(getString(R.string.wrong_operation))
            }

        } else {
            result!!.setCurrentText("")
        }
    }

    private fun getFormattedNumber(value: Double): String {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        return formatter.format(value).toString()
    }

    override fun onAnimationStart(animation: Animator) {
        resultScreen!!.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    override fun onAnimationEnd(animation: Animator) {
        resultScreen!!.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        operations!!.text = ""
    }

    override fun onAnimationCancel(animation: Animator) {

    }

    override fun onAnimationRepeat(animation: Animator) {

    }
}
