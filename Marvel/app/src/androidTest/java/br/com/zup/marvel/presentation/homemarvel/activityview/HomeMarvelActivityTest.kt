package br.com.zup.marvel.presentation.homemarvel.activityview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.zup.marvel.R
import br.com.zup.marvel.data.repository.AuthenticationRepository
import br.com.zup.marvel.presentation.homemarvel.viewmodel.HomeViewModel
import br.com.zup.marvel.presentation.login.activityview.LoginActivity
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeMarvelActivityTest {
    private var authenticationRepositoryMockk: AuthenticationRepository = mockk(relaxed = true)
    private var viewModel = HomeViewModel(authenticationRepositoryMockk)
    private lateinit var stringToPasswordCorrect: String
    private lateinit var stringToEmailCorrect: String


    @Before
    fun initValidString() {
        stringToPasswordCorrect = "123456789"
        stringToEmailCorrect = "thay@thay.com"

        every {
            authenticationRepositoryMockk.registerUser(
                stringToEmailCorrect,
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


    @get:Rule
    var activityMarvelRule: ActivityScenarioRule<HomeMarvelActivity> =
        ActivityScenarioRule(HomeMarvelActivity::class.java)

    @Test

    fun checkValidationLoginActivity_showNameCorrect() {
        onView(withId(R.id.tv_userMessage)).check(
            ViewAssertions.matches(withText("Olá, thay - $stringToEmailCorrect! Esses são alguns dos personagens da Marvel."))
        )

    }
}
