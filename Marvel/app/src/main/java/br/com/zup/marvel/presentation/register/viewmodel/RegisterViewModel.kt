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

class RegisterViewModel : ViewModel() {

    private val authenticationRepository = AuthenticationRepository()

    private val _registerState = MutableLiveData<Users>()
    val registerState: LiveData<Users> = _registerState

    private val _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDateUsers(users: Users) {
        when {
            users.name.isEmpty() -> {
                _errorState.value = NAME_ERROR_MESSAGE
            }
            users.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            users.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                registerUsers(users)
            }
        }
    }

    private fun registerUsers(user: Users) {
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
