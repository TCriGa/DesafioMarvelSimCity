package br.com.zup.marvel.presentation.register.activityview

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.marvel.TITLE_REGISTER
import br.com.zup.marvel.USERS_KEY
import br.com.zup.marvel.databinding.ActivityRegisterBinding
import br.com.zup.marvel.domain.model.Users
import br.com.zup.marvel.presentation.homemarvel.activityview.HomeMarvelActivity
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = TITLE_REGISTER

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
        viewModel.errorState.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun goToHomeMarvel(users: Users) {
        startActivity(Intent(this, HomeMarvelActivity::class.java)
            .apply { putExtra(USERS_KEY, users) })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()

            return true
        }
        return super.onOptionsItemSelected(item)
    }

}