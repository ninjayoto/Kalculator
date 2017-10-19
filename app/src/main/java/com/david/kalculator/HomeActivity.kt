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
import android.widget.TextView

import java.util.Arrays
import java.util.Locale

import com.david.kalculator.MathExpression.removeLastCharacter
import com.david.kalculator.MathExpression.addCharacter

import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.element_calculator.*

class HomeActivity : AppCompatActivity(), TextWatcher, Animator.AnimatorListener {

    private var buttons : MutableList<Button>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initializeViewComponents()
        initializeViewListeners()
    }

    private fun initializeViewComponents() {
        buttons = Arrays.asList<Button>(bt_parenthesis_start, bt_parenthesis_end, bt_addition,
                bt_subtraction, bt_multiplication, bt_division, bt_exponentiation, bt_square_root,
                bt_logarithm10, bt_natural_logarithm, bt_factorial, bt0, bt00, bt1, bt2, bt3, bt4,
                bt5, bt6, bt7, bt8, bt9, bt_dot)
    }

    private fun initializeViewListeners() {
        operations.addTextChangedListener(this)

        result.setFactory {
            val resultView = TextView(this@HomeActivity)
            resultView.gravity = Gravity.CENTER
            resultView.textSize = 30f
            resultView
        }

        result.setInAnimation(this, R.anim.result_anim)

        bt_clear.setOnClickListener {
            val expression = operations.text.toString()
            operations.text = expression.removeLastCharacter()
        }

        bt_clear.setOnLongClickListener {
            circularRevealCard(result_screen)
            true
        }

        bt_equal.setOnClickListener {
            result.setText((result.currentView as TextView).text)
            (result.currentView as TextView).setTextColor(ContextCompat.getColor(this@HomeActivity, R.color.colorAccent))
        }

        for (button in buttons!!) {
            button.setOnClickListener {
                val expression = operations.text.toString()
                operations.text = expression.addCharacter(button.tag.toString())
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun circularRevealCard(view: View) {
        val finalRadius = Math.max(view.width, view.height).toFloat()

        val circularReveal = ViewAnimationUtils.createCircularReveal(
                view,
                0,
                view.height,
                0f,
                finalRadius * 1.5f)

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
                (result.currentView as TextView).setTextColor(ContextCompat.getColor(this, R.color.secondaryText))
                result.setCurrentText(getFormattedNumber(MathCalculator.calculate(expression)))

            } catch (exception: IllegalArgumentException) {
                (result.currentView as TextView).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                result.setCurrentText(getString(R.string.wrong_expression))

            } catch (exception: ArithmeticException) {
                (result.currentView as TextView).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                result.setCurrentText(getString(R.string.wrong_operation))
            }

        } else {
            result.setCurrentText("")
        }
    }

    private fun getFormattedNumber(value: Double): String {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        return formatter.format(value).toString()
    }

    override fun onAnimationStart(animation: Animator) {
        result_screen.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    override fun onAnimationEnd(animation: Animator) {
        result_screen.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        operations.text = ""
    }

    override fun onAnimationCancel(animation: Animator) {

    }

    override fun onAnimationRepeat(animation: Animator) {

    }
}
