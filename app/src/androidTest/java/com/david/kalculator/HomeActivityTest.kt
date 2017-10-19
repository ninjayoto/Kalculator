package com.david.kalculator

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.rule.ActivityTestRule
import android.view.View
import android.widget.TextView

import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import junitparams.JUnitParamsRunner
import junitparams.Parameters

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.support.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf


// You can use another runner with instrumented Android tests as long as you set
// testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner" in your module
// build.gradle file.
//
// If that is set, you do not need to explicitly set @RunWith(AndroidJUnit4.class)
// in your instrumented tests.

@RunWith(JUnitParamsRunner::class)
class HomeActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Parameters(method = "getValidOperationsViewInput")
    @Test
    fun clickButtonShouldAddExpectedTextToOperationsView(
            buttonId: Int, previousText: String, expectedText: String) {

        onView(withId(R.id.operations)).perform(setTextInTextView(previousText))

        onView(withId(buttonId)).perform(click())

        onView(withId(R.id.operations)).check(matches(withText(expectedText)))
    }

    private fun getValidOperationsViewInput(): Array<Any> {
        return arrayOf(
                arrayOf(R.id.bt1, "", "1"),
                arrayOf(R.id.bt2, "42", "422"),
                arrayOf(R.id.bt3, "42.", "42.3"),
                arrayOf(R.id.bt4, "42.3(", "42.3(4"),
                arrayOf(R.id.bt5, "55", "555"),
                arrayOf(R.id.bt6, "0.", "0.6"),
                arrayOf(R.id.bt7, "fact(", "fact(7"),
                arrayOf(R.id.bt8, "7^", "7^8"),
                arrayOf(R.id.bt9, "log(", "log(9"),
                arrayOf(R.id.bt0, "(", "(0"),
                arrayOf(R.id.bt00, "12", "1200"),
                arrayOf(R.id.bt_dot, "1", "1."),
                arrayOf(R.id.bt_addition, "3*", "3+"),
                arrayOf(R.id.bt_subtraction, "12.", "12-"),
                arrayOf(R.id.bt_multiplication, "12.3", "12.3*"),
                arrayOf(R.id.bt_division, "12.3-", "12.3/"),
                arrayOf(R.id.bt_exponentiation, "2", "2^"),
                arrayOf(R.id.bt_square_root, "3", "3sqrt("),
                arrayOf(R.id.bt_logarithm10, "3", "3log("),
                arrayOf(R.id.bt_natural_logarithm, "3.4", "3.4ln("),
                arrayOf(R.id.bt_factorial, "(3/4)", "(3/4)fact("),
                arrayOf(R.id.bt_parenthesis_start, "55.6^3", "55.6^3("),
                arrayOf(R.id.bt_parenthesis_end, "(33+33", "(33+33)"))
    }

    @Parameters(method = "getValidResultViewInput")
    @Test
    fun clickButtonShouldAddExpectedValuesToResultView(
            buttonId: Int, previousText: String, expectedText: String) {

        onView(withId(R.id.operations)).perform(setTextInTextView(previousText))

        onView(withId(buttonId)).perform(click())

        onView(allOf(withParent(withId(R.id.result)), isCompletelyDisplayed())).check(matches(withText(expectedText)))
    }

    //Be careful because the third parameter that corresponds with the expectedText is
    //influenced by the Locale.Default user preferences. In this example I used the spanish Locale settings

    private fun getValidResultViewInput(): Array<Any> {
        return arrayOf(
                arrayOf(R.id.bt1, "12+", "13"),
                arrayOf(R.id.bt2, "3^", "9"),
                arrayOf(R.id.bt3, "3.", "3,3"),
                arrayOf(R.id.bt4, "42.3(", "169,2"),
                arrayOf(R.id.bt5, "55", "555"),
                arrayOf(R.id.bt6, "0.", "0,6"),
                arrayOf(R.id.bt7, "fact(", "5.040"),
                arrayOf(R.id.bt8, "7^", "5.764.801"),
                arrayOf(R.id.bt9, "log(", "0,954"),
                arrayOf(R.id.bt0, "7/", "Wrong operation"),
                arrayOf(R.id.bt00, "12", "1.200"),
                arrayOf(R.id.bt_dot, "1", "1"),
                arrayOf(R.id.bt_addition, "3*", "3"),
                arrayOf(R.id.bt_subtraction, "12.", "12"),
                arrayOf(R.id.bt_multiplication, "12.3", "12,3"),
                arrayOf(R.id.bt_division, "12.3-", "12,3"),
                arrayOf(R.id.bt_exponentiation, "2", "2"),
                arrayOf(R.id.bt_square_root, "3", "3"),
                arrayOf(R.id.bt_logarithm10, "3", "3"),
                arrayOf(R.id.bt_natural_logarithm, "3.4", "3,4"),
                arrayOf(R.id.bt_factorial, "(3/4)", "0,75"),
                arrayOf(R.id.bt_parenthesis_start, "55.6^3", "171.879,616"),
                arrayOf(R.id.bt_parenthesis_end, "(33+33", "66"))

    }

    companion object {

        fun setTextInTextView(value: String): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return allOf(isDisplayed(), isAssignableFrom(TextView::class.java))
                }

                override fun perform(uiController: UiController, view: View) {
                    (view as TextView).text = value
                }

                override fun getDescription(): String {
                    return "replace text"
                }
            }
        }
    }

}