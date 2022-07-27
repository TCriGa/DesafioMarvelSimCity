package br.com.zup.marvel.presentation.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.CREATE_USER_ERROR_MESSAGE
import br.com.zup.marvel.EMAIL_ERROR_MESSAGE
import br.com.zup.marvel.NAME_ERROR_MESSAGE
import br.com.zup.marvel.PASSWORD_ERROR_MESSAGE
import br.com.zup.marvel.data.repository.AuthenticationRepository
import br.com.zup.marvel.domain.model.Users
import java.util.regex.Pattern

class RegisterViewModel(private val authenticationRepository: AuthenticationRepository = AuthenticationRepository()) :
    ViewModel() {


    private val _registerState = MutableLiveData<Users>()
    val registerState: LiveData<Users> = _registerState

    private val _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDateUsers(users: Users) {
        if (!haveErrorsDateUsers(users))
            registerUsers(users)
    }

    fun haveErrorsDateUsers(users: Users): Boolean {
        val emailPattern: Pattern =
            Pattern.compile(
                "[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            )
        val passwordPattern: Pattern =
            Pattern.compile(
                "^(?=.*).{8,}\$"
            )
        val namePattern: Pattern =
            Pattern.compile(
                "(.*[a-z]){3}"
            )

        when {
            users.name.isEmpty() || namePattern.matcher(users.name).matches().not() -> {
                _errorState.value = NAME_ERROR_MESSAGE
                return true
            }
            users.email.isEmpty() || emailPattern.matcher(users.email).matches().not() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
                return true
            }
            users.password.isEmpty() || passwordPattern.matcher(users.password).matches().not() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
                return true
            }
            else -> {
                return false
            }

        }
    }

    fun registerUsers(user: Users) {
        try {
            authenticationRepository.registerUser(
                user.email,
                user.password
            ).addOnSuccessListener {

                authenticationRepository.updateUserProfile(user.name)?.addOnSuccessListener {
                    _registerState.value = user
                }

            }.addOnFailureListener {
                _errorState.value = CREATE_USER_ERROR_MESSAGE + it.message
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }
}
