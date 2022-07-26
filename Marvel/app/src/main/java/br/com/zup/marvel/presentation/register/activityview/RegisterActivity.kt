package br.com.zup.marvel.presentation.register.activityview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.marvel.USERS_KEY
import br.com.zup.marvel.databinding.ActivityRegisterBinding
import br.com.zup.marvel.domain.model.Users
import br.com.zup.marvel.presentation.home.view.HomeActivity
import br.com.zup.marvel.presentation.register.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        clickRegister()
        initObserver()
    }

    private fun clickRegister() {
        binding.buttonRegister.setOnClickListener {
            val getDataUsers = getDataUsers()
            viewModel.validateDateUsers(getDataUsers)
        }
    }

    private fun getDataUsers(): Users {
        val name = binding.editNameRegister.text.toString()
        val email = binding.editEmailRegister.text.toString()
        val password = binding.editPasswordRegister.text.toString()
        return Users(name = name, email = email, password = password)
    }

    private fun initObserver() {
        viewModel.registerState.observe(this) {
            goToHomeMarvel(it)
        }
        viewModel.errorState.observe(this){
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun goToHomeMarvel(users: Users) {
        startActivity(Intent(this, HomeActivity::class.java)
            .apply { putExtra(USERS_KEY, users) })
    }
}