package com.wahyudwi.githubapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wahyudwi.githubapp.R
import com.wahyudwi.githubapp.data.model.DetailUserResponse
import com.wahyudwi.githubapp.databinding.ActivityDetailBinding
import com.wahyudwi.githubapp.ui.followers.FollowersFragment
import com.wahyudwi.githubapp.ui.following.FollowingFragment
import com.wahyudwi.githubapp.utils.ViewModelFactory
import com.wahyudwi.githubapp.utils.ViewPagerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private var isChecked = false
    private var isShow = true
    private var scrollRange = -1
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        setViewPager()

        viewModel = obtainViewModel(this as AppCompatActivity)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        username = intent.getStringExtra(EXTRA_USERNAME) as String

        viewModel.setUserDetail(username)
        viewModel.detailUser.observe(this) { detailMovie ->
            populateContentDetail(detailMovie)
            showTitleCollapse("$username Profile")
        }
        isDarkModeTabLayout()

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    isChecked = if (count > 0) {
                        isFavorited(true)
                        true
                    } else {
                        isFavorited(false)
                        false
                    }
                }
            }
        }

        binding.fabFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                viewModel.addToFavorite(id, username, avatarUrl!!)
            } else {
                viewModel.removeFromFavorite(id)
            }
            isFavorited(isChecked)
        }
    }

    fun getData(): String = username

    private fun populateContentDetail(detailMovie: DetailUserResponse) {
        binding.apply {
            tvDetailName.text = detailMovie.name
            tvDetailUser.text = getString(R.string.username, detailMovie.login)
            tvDetailCompany.text = getString(R.string.company, detailMovie.company)
            tvDetailLocation.text = getString(R.string.location, detailMovie.location)
            tvDetailRepository.text =
                getString(R.string.repositories, detailMovie.publicRepos.toString())
            tvDetailFollowers.text =
                getString(R.string.followers_s, detailMovie.followers.toString())
            tvDetailFollowing.text =
                getString(R.string.following_s, detailMovie.following.toString())

            ivDetailUser.load(detailMovie.avatarUrl) {
                placeholder(R.drawable.ic_placeholder)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun setViewPager() {
        val listFragment = listOf(FollowersFragment(), FollowingFragment())
        val titleTab = listOf(getString(R.string.followers), getString(R.string.following))

        binding.viewPager.adapter =
            ViewPagerAdapter(listFragment, this.supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleTab[position]
        }.attach()
    }

    private fun showTitleCollapse(statusBar: String) {
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }

            if (scrollRange + verticalOffset == 0) {
                binding.collapsingToolbar.title = statusBar
                binding.collapsingToolbar.setCollapsedTitleTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                isShow = true
            } else if (isShow) {
                binding.collapsingToolbar.title = " "
                isShow = false
            }
        })
    }

    private fun isFavorited(state: Boolean) {
        val fab = binding.fabFavorite
        if (state) {
            fab.setImageResource(R.drawable.ic_favorited)
        } else {
            fab.setImageResource(R.drawable.ic_favorite)
        }
    }

    private fun isDarkModeTabLayout() {
        viewModel.getThemeSettings().observe(this) {
            binding.apply {
                if (it) {
                    tabLayout.setSelectedTabIndicatorColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.teal_200
                        )
                    )

                    tabLayout.setTabTextColors(
                        ContextCompat.getColor(this@DetailActivity, R.color.unselected),
                        ContextCompat.getColor(this@DetailActivity, R.color.teal_200)
                    )

                } else {
                    tabLayout.setSelectedTabIndicatorColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.black_500
                        )
                    )

                    tabLayout.setTabTextColors(
                        ContextCompat.getColor(this@DetailActivity, R.color.unselected),
                        ContextCompat.getColor(this@DetailActivity, R.color.black_500)
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"
    }
}
