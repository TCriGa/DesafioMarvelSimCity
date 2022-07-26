package br.com.zup.marvel.presentation.login.activityview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.marvel.TITLE_LOGIN
import br.com.zup.marvel.USERS_KEY
import br.com.zup.marvel.databinding.ActivityLoginBinding
import br.com.zup.marvel.domain.model.Users
import br.com.zup.marvel.presentation.homemarvel.activityview.HomeMarvelActivity
import br.com.zup.marvel.presentation.login.viewmodel.LoginViewModel
import br.com.zup.marvel.presentation.register.activityview.RegisterActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title = TITLE_LOGIN
    }

    override fun onResume() {
        super.onResume()
        clickButtonCreateRegister()
        clickButtonLogin()
        initObserver()
    }

    private fun getDataUsers(): Users {
        val email = binding.editEmailLogin.text.toString()
        val password = binding.editPassword.text.toString()

        return Users(email = email, password = password)
    }

    private fun initObserver() {
        viewModel.loginState.observe(this) {
            goToHomeMarvel(it)
        }

        viewModel.errorState.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun goToHomeMarvel(users: Users) {
        startActivity(Intent(this, HomeMarvelActivity::class.java)
            .apply { putExtra(USERS_KEY, users) })
    }

    private fun clickButtonLogin() {
        binding.buttonLogin.setOnClickListener {
            val getDataUsers = getDataUsers()
            viewModel.validateDataUser(getDataUsers)
        }
    }

    private fun clickButtonCreateRegister() {
        binding.tvRegisterNow.setOnClickListener {
            goToRegister()
        }
    }

}
