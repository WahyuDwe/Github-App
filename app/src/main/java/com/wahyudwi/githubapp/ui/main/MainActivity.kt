package com.wahyudwi.githubapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyudwi.githubapp.R
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.databinding.ActivityMainBinding
import com.wahyudwi.githubapp.ui.favorite.FavoriteActivity
import com.wahyudwi.githubapp.ui.settings.SettingActivity
import com.wahyudwi.githubapp.utils.Adapter
import com.wahyudwi.githubapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val listUser: ArrayList<SearchUser> = arrayListOf()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this as AppCompatActivity)
        adapter = Adapter()
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchView = menu.findItem(R.id.search_menu).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.maxWidth = Int.MAX_VALUE
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchData(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchData(query)
        }

        if (query.isNullOrBlank()) {
            listUser.clear()
            adapter.setData(listUser)
            isLoading(false)
            isImageShow(true)
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.option_menu -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    private fun searchData(query: String) {
        isLoading(true)
        isImageShow(false)
        viewModel.getSearchUser(query).observe(this) { list ->
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

