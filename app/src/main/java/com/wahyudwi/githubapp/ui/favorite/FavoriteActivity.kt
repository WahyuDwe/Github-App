package com.wahyudwi.githubapp.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyudwi.githubapp.data.local.entity.FavoriteEntity
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.databinding.ActivityFavoriteBinding
import com.wahyudwi.githubapp.utils.Adapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: Adapter
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = Adapter()
        binding.rvUserFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = this@FavoriteActivity.adapter
        }

        viewModel.getFavoritedUser().observe(this) {
            val list = mapList(it)
            if (it != null && it.isNotEmpty()) {
                isShowIllustration(false)
                adapter.setData(list)
            } else {
                isShowIllustration(true)
                adapter.setData(list)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun isShowIllustration(state: Boolean) {
        binding.ivNoneFavorite.isVisible = state
        binding.tvNoneFavorite.isVisible = state
    }

    private fun mapList(listFavorite: List<FavoriteEntity>): ArrayList<SearchUser> {
        val listUser = ArrayList<SearchUser>()
        for (user in listFavorite) {
            val userMapped = SearchUser(
                user.id,
                user.login,
                user.avatar_url
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}