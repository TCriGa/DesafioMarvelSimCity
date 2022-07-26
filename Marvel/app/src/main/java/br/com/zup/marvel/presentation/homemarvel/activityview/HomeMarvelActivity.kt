package br.com.zup.marvel.presentation.homemarvel.activityview

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.marvel.MARVEL_KEY
import br.com.zup.marvel.R
import br.com.zup.marvel.data.model.Marvel
import br.com.zup.marvel.databinding.ActivityHomeMarvelBinding
import br.com.zup.marvel.presentation.detalhe.activityview.DetalheActivity
import br.com.zup.marvel.presentation.homemarvel.viewmodel.HomeViewModel
import br.com.zup.marvel.presentation.login.activityview.LoginActivity

class HomeMarvelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeMarvelBinding

    private val adapter: HomeMarvelAdapter by lazy {
        HomeMarvelAdapter(arrayListOf(), this::goToDetail)
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMarvelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getListMarvel()

    }

    override fun onResume() {
        super.onResume()
        showUsesData()
        setUpRecyclerView()
        initObserver()
    }

    private fun setUpRecyclerView() {
        binding.rvHerois.adapter = adapter
        binding.rvHerois.layoutManager = LinearLayoutManager(this)
    }

    private fun initObserver() {
        viewModel.marvelListState.observe(this) {
            adapter.updateMarvelList(it.toMutableList())
        }

    }

    private fun goToDetail(marvel: Marvel) {
        val intent = Intent(this, DetalheActivity::class.java).apply {
            putExtra(MARVEL_KEY, marvel)
        }
        startActivity(intent)
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun showUsesData() {
        val name = viewModel.getUsersName()
        val email = viewModel.getUsersEmail()
        binding.tvUserMessage.text = getString(R.string.welcome_messages, "$name - $email")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                viewModel.logoutUser()
                this.finish()
                goToLogin()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}