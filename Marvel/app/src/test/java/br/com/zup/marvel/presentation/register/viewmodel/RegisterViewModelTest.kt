package br.com.zup.marvel.presentation.register.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.zup.marvel.EMAIL_ERROR_MESSAGE
import br.com.zup.marvel.NAME_ERROR_MESSAGE
import br.com.zup.marvel.PASSWORD_ERROR_MESSAGE
import br.com.zup.marvel.data.repository.AuthenticationRepository
import br.com.zup.marvel.domain.model.Users
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterViewModelTest {

    private var authenticationRepository: AuthenticationRepository = mockk(relaxed = true)
    private val viewModel = RegisterViewModel(authenticationRepository)

    @Before
    fun beforeView() {
        every { authenticationRepository.registerUser(any(), any()) } returns mockk(relaxed = true)
        every { authenticationRepository.auth } returns mockk(relaxed = true)

    }

    @After
    fun tearDown() {
        unmockkAll()
    }


    @Test
    fun `validating user with invalid name`() {

        //GIVEN - PREPARAR
        val invalidatedUserName = Users("Th", "thaygmail.com", "12345")

        //WHEN - EXECUTAR
        viewModel.haveErrorsDateUsers(invalidatedUserName)

        //Then - Validar
        assert(viewModel.errorState.value == NAME_ERROR_MESSAGE)

    }
    @Test
    fun `validating user with invalid email`() {

        //GIVEN - PREPARAR
        val invalidatedUserEmail = Users("Thayana", "thaygmail.com", "12345")

        //WHEN - EXECUTAR
        viewModel.haveErrorsDateUsers(invalidatedUserEmail)

       //Then - Validar
        assert(viewModel.errorState.value == EMAIL_ERROR_MESSAGE)
    }

    @Test
    fun `validating user with invalid password`() {

        //GIVEN - PREPARAR
        val invalidatedUserEmail = Users("Thayana", "thay@gmail.com", "123456")

        //WHEN - EXECUTAR
        viewModel.haveErrorsDateUsers(invalidatedUserEmail)

        //Then - Validar
        assert(viewModel.errorState.value == PASSWORD_ERROR_MESSAGE)
    }
}
