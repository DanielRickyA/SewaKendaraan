package com.example.ugd3_c_10898


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun registerActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(500)

        val materialButton = onView(
            allOf(
                withId(R.id.btnActionRegister), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.registerLayout),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText = onView(
            allOf(
                withId(R.id.etUsername),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputUsername2),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("1"), closeSoftKeyboard())



        val materialButton2 = onView(
            allOf(
                withId(R.id.btnActionRegister), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.registerLayout),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.etPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputPassword2),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("1"), closeSoftKeyboard())


        val materialButton3 = onView(
            allOf(
                withId(R.id.btnActionRegister), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.registerLayout),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.etEmail),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("1"), closeSoftKeyboard())



        val materialButton4 = onView(
            allOf(
                withId(R.id.btnActionRegister), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.registerLayout),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.etEmail), withText("1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(click())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.etEmail), withText("1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("daniel@gmail.com"))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.etEmail), withText("daniel@gmail.com"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(closeSoftKeyboard())



        val materialButton5 = onView(
            allOf(
                withId(R.id.btnActionRegister), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.registerLayout),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.inputTL),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputTanggal),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(replaceText("12-12-2002"), closeSoftKeyboard())



        val materialButton6 = onView(
            allOf(
                withId(R.id.btnActionRegister), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.registerLayout),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.etNumber),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputNoTelp),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText8.perform(replaceText("111"), closeSoftKeyboard())



        val materialButton7 = onView(
            allOf(
                withId(R.id.btnActionRegister), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.registerLayout),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText9 = onView(
            allOf(
                withId(R.id.etNumber), withText("111"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputNoTelp),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText9.perform(click())

        val textInputEditText10 = onView(
            allOf(
                withId(R.id.etNumber), withText("111"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputNoTelp),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText10.perform(replaceText("082221922011"))

        val textInputEditText11 = onView(
            allOf(
                withId(R.id.etNumber), withText("082221922011"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputNoTelp),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText11.perform(closeSoftKeyboard())



        val materialButton8 = onView(
            allOf(
                withId(R.id.btnActionRegister), withText("Register"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            withId(R.id.registerLayout),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())
        onView(isRoot()).perform(waitFor(3000))
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
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay: Long): ViewAction?{
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>{
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
