package com.modarb.android.ui.onboarding.activities


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.modarb.android.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(WelcomeScreenActivity::class.java)

    @Test
    fun registerTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.startButton), withText("Start"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 6
                ), isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 2
                ), isDisplayed()
            )
        )
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 2
                ), isDisplayed()
            )
        )
        materialButton3.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 2
                ), isDisplayed()
            )
        )
        materialButton4.perform(click())

        val linearLayout = onView(
            allOf(
                withId(R.id.container1), childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")), 1
                    ), 0
                ), isDisplayed()
            )
        )
        linearLayout.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 1
                ), isDisplayed()
            )
        )
        materialButton5.perform(click())

        val view = onView(
            allOf(
                withId(R.id.maleGenderView), childAtPosition(
                    childAtPosition(
                        withId(R.id.container), 0
                    ), 2
                ), isDisplayed()
            )
        )
        view.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 1
                ), isDisplayed()
            )
        )
        materialButton6.perform(click())

        val materialButton7 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 1
                ), isDisplayed()
            )
        )
        materialButton7.perform(click())

        val materialButton8 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 1
                ), isDisplayed()
            )
        )
        materialButton8.perform(click())

        val linearLayout2 = onView(
            allOf(
                withId(R.id.container1), childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")), 1
                    ), 0
                ), isDisplayed()
            )
        )
        linearLayout2.perform(click())

        val materialButton9 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 1
                ), isDisplayed()
            )
        )
        materialButton9.perform(click())

        val linearLayout3 = onView(
            allOf(
                withId(R.id.container3), childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")), 1
                    ), 2
                ), isDisplayed()
            )
        )
        linearLayout3.perform(click())

        val materialButton10 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 1
                ), isDisplayed()
            )
        )
        materialButton10.perform(click())

        val materialCheckBox = onView(
            allOf(
                withId(R.id.checkBox), withText("Dumbbells"), childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView), 1
                    ), 0
                ), isDisplayed()
            )
        )
        materialCheckBox.perform(click())

        val materialCheckBox2 = onView(
            allOf(
                withId(R.id.checkBox), withText("Gym machines"), childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView), 2
                    ), 0
                ), isDisplayed()
            )
        )
        materialCheckBox2.perform(click())

        val materialButton11 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 1
                ), isDisplayed()
            )
        )
        materialButton11.perform(click())

        val materialCheckBox3 = onView(
            allOf(
                withId(R.id.checkBox), withText("Arms"), childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView), 3
                    ), 0
                ), isDisplayed()
            )
        )
        materialCheckBox3.perform(click())

        val materialCheckBox4 = onView(
            allOf(
                withId(R.id.checkBox), withText("Knees"), childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView), 4
                    ), 0
                ), isDisplayed()
            )
        )
        materialCheckBox4.perform(click())

        val materialButton12 = onView(
            allOf(
                withId(R.id.nextButton), withText("Next"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 1
                ), isDisplayed()
            )
        )
        materialButton12.perform(click())

        val materialButton13 = onView(
            allOf(
                withId(R.id.nextButton), withText("Continue"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 1
                ), isDisplayed()
            )
        )
        materialButton13.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.nameEditText), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout), 0
                    ), 0
                ), isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("amrr@gmail.com"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.emailEditText2), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout2), 0
                    ), 0
                ), isDisplayed()
            )
        )
        val randomEmail = generateRandomEmail("amr")

        textInputEditText2.perform(replaceText(randomEmail), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.nameEditText), withText("amrr@gmail.com"), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout), 0
                    ), 0
                ), isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("test ui"))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.nameEditText), withText("test ui"), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout), 0
                    ), 0
                ), isDisplayed()
            )
        )
        textInputEditText4.perform(closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.passwordEditText), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout3), 0
                    ), 0
                ), isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("12345678"), closeSoftKeyboard())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.passwordEditText), withText("12345678"), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout3), 0
                    ), 0
                ), isDisplayed()
            )
        )
        textInputEditText6.perform(pressImeActionButton())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.confirmPasswordEditText), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout4), 0
                    ), 0
                ), isDisplayed()
            )
        )
        textInputEditText7.perform(replaceText("12345678"), closeSoftKeyboard())

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.confirmPasswordEditText), withText("12345678"), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout4), 0
                    ), 0
                ), isDisplayed()
            )
        )
        textInputEditText8.perform(pressImeActionButton())

        val materialButton14 = onView(
            allOf(
                withId(R.id.registerButton), withText("Register"), childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 7
                ), isDisplayed()
            )
        )
        materialButton14.perform(click())

        Thread.sleep(3000)


    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(
                    position
                )
            }
        }
    }

    private fun generateRandomEmail(nameSubstring: String): String {
        val random = Random(System.currentTimeMillis())
        val emailPrefix = "user_$nameSubstring${random.nextInt(1000)}"
        return "$emailPrefix@gmail.com"
    }
}
