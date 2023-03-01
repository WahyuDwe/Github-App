package com.wahyudwi.githubapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wahyudwi.githubapp.R
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.databinding.ActivityMainBinding
import com.wahyudwi.githubapp.ui.favorite.FavoriteActivity
import com.wahyudwi.githubapp.ui.settings.SettingActivity
import com.wahyudwi.githubapp.utils.Adapter

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var closeButton: ImageView? = null
    private val listUser: ArrayList<SearchUser> = arrayListOf()
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = Adapter()
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchView = menu.findItem(R.id.search_menu).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setIconifiedByDefault(true)
        searchView.isSubmitButtonEnabled = true
        closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        closeButton?.setOnClickListener {
            searchView.onActionViewCollapsed()
            listUser.clear()
            adapter.setData(listUser)
            isImageShow(true)
        }
        searchView.maxWidth = Int.MAX_VALUE
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchData(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean = false

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.setting_menu -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun searchData(query: String) {
        isLoading(true)
        isImageShow(false)
        viewModel.getSearchUser(query).observe(this) { list ->
            if (list.isEmpty()) {
                isLoading(false)
                Snackbar.make(binding.root, getString(R.string.user_not_found), Snackbar.LENGTH_SHORT).show()
            }

            list.let {
                isLoading(false)
                adapter.setData(it)
            }
        }
    }

    private fun isLoading(state: Boolean) {
        binding.apply {
            progressbar.isVisible = state
            rvUser.isGone = state
        }
    }

    private fun isImageShow(state: Boolean) {
        binding.apply {
            ivSearch.isVisible = state
            tvSearch.isVisible = state
            tvExtended.isVisible = state
        }
    }
}

