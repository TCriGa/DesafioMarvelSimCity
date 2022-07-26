package br.com.zup.marvel.presentation.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.EMAIL_ERROR_MESSAGE
import br.com.zup.marvel.LOGIN_ERROR_MESSAGE
import br.com.zup.marvel.PASSWORD_ERROR_MESSAGE
import br.com.zup.marvel.data.repository.AuthenticationRepository
import br.com.zup.marvel.domain.model.Users

class LoginViewModel:ViewModel() {
    private val authenticationRepository = AuthenticationRepository()

    private var _loginState = MutableLiveData<Users>()
    val loginState: LiveData<Users> = _loginState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDataUser(users: Users) {
        when {
            users.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            users.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                loginUser(users)
            }
        }
    }

    private fun loginUser(users: Users) {
        try {
            authenticationRepository.loginUser(
                users.email,
                users.password
            ).addOnSuccessListener {
                _loginState.value = users
            }.addOnFailureListener {
                _errorState.value = LOGIN_ERROR_MESSAGE + it.message
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }
}