package br.com.zup.marvel.presentation.login.activityview

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.zup.marvel.LOGIN_ERROR_MESSAGE
import br.com.zup.marvel.R
import br.com.zup.marvel.data.repository.AuthenticationRepository
import br.com.zup.marvel.presentation.login.viewmodel.LoginViewModel
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.core.AllOf
import org.hamcrest.core.StringContains
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    private var authenticationRepositoryMockk: AuthenticationRepository = mockk(relaxed = true)
    private var viewModel = LoginViewModel(authenticationRepositoryMockk)
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
        every {
            authenticationRepositoryMockk.loginUser(
                stringToEmailIncorrect,
                stringToPasswordCorrect
            )
        } returns mockk(relaxed = true)
        every { authenticationRepositoryMockk.auth } returns mockk(relaxed = true)
    }

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    var activityRule: ActivityScenarioRule<LoginActivity> =
        ActivityScenarioRule(LoginActivity::class.java)


    @Test
    fun checkValidationLoginActivity_emailIncorrect() {
        onView(withId(R.id.edit_emailLogin))
            .perform(replaceText(stringToEmailCorrect))
        onView(withId(R.id.edit_password))
            .perform(replaceText(stringToPasswordIncorrect))
        closeSoftKeyboard()
        onView(withId(R.id.button_login))
            .perform(click())
//        onView(withText(com.google.android.material.R.id.snackbar_text)).check(
//            ViewAssertions.matches(withText(LOGIN_ERROR_MESSAGE)))
        onView(
            allOf(
                withText(LOGIN_ERROR_MESSAGE),
                withId(com.google.android.material.R.id.snackbar_text)
            )
        )

    }

    @Test
    fun checkValidationLoginActivity_passwordIncorrect() {
        onView(withId(R.id.edit_emailLogin))
            .perform(replaceText(stringToEmailCorrect))
        onView(withId(R.id.edit_password))
            .perform(replaceText(stringToPasswordIncorrect))
        closeSoftKeyboard()
        onView(withId(R.id.button_login))
            .perform(click())
//        onView(withText(com.google.android.material.R.id.snackbar_text)).check(
//            ViewAssertions.matches(withText(StringContains.containsString(LOGIN_ERROR_MESSAGE))))
//        )
        onView(
            allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                withText(LOGIN_ERROR_MESSAGE)
            )
        )
    }


}