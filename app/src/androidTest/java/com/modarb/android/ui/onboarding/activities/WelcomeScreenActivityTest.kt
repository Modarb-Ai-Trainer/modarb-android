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
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@LargeTest
@RunWith(AndroidJUnit4::class)
class WelcomeScreenActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(WelcomeScreenActivity::class.java)

    @Test
    fun welcomeScreenActivityTest() {
        val materialTextView = onView(
            allOf(
                withId(R.id.loginTextView),
                withText("continue with existing account"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content), 0
                    ), 5
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.emailEditText), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout1), 0
                    ), 0
                ), isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("z@gmail.com"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.passwordEditText), childAtPosition(
                    childAtPosition(
                        withId(R.id.textInputLayout2), 0
                    ), 0
                ), isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("11111111"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.loginBtn), withText("Login"), childAtPosition(
                    childAtPosition(
                        withId(com.google.android.material.R.id.design_bottom_sheet), 0
                    ), 2
                ), isDisplayed()
            )
        )

        val latch = CountDownLatch(1)

        materialButton.perform(click())

        mActivityScenarioRule.scenario.onActivity { activity ->
            activity.viewModel.loginResponse.observe(activity) { response ->
                if (response.isSuccessful) {
                    latch.countDown()
                }
            }
        }

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
}
