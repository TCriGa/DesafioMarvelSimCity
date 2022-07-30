package br.com.zup.marvel.presentation.login.activityview

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.zup.marvel.LOGIN_ERROR_MESSAGE
import br.com.zup.marvel.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private lateinit var stringToPasswordCorrect: String
    private lateinit var stringToPasswordIncorrect: String
    private lateinit var stringToEmailCorrect: String
    private lateinit var stringToEmailIncorrect: String

    @Before
    fun initValidString() {
        stringToPasswordCorrect = "123456789"
        stringToPasswordIncorrect = "12345"
        stringToEmailCorrect = "thay@thay.com"
        stringToEmailIncorrect = "thay.com"

    }

    @get:Rule
    var activityRule: ActivityScenarioRule<LoginActivity> =
        ActivityScenarioRule(LoginActivity::class.java)


    @Test
    fun checkValidationLoginActivity_emailIncorrect() {
        onView(withId(R.id.edit_emailLogin))
            .perform(typeText(stringToEmailIncorrect))
        closeSoftKeyboard()
        onView(withId(R.id.edit_password))
            .perform(typeText(stringToPasswordCorrect))
        closeSoftKeyboard()
        onView(withId(R.id.button_login))
            .perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(
            ViewAssertions.matches(withText(LOGIN_ERROR_MESSAGE))
        )

    }

    @Test
    fun checkValidationLoginActivity_passwordIncorrect() {
        onView(withId(R.id.edit_emailLogin))
            .perform(typeText(stringToEmailCorrect))
        closeSoftKeyboard()
        onView(withId(R.id.edit_password))
            .perform(typeText(stringToPasswordIncorrect))
        closeSoftKeyboard()
        onView(withId(R.id.button_login))
            .perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(
            ViewAssertions.matches(withText(LOGIN_ERROR_MESSAGE))
        )
    }


}