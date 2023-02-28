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
import com.wahyudwi.githubapp.utils.ViewPagerAdapter

class DetailActivity : AppCompatActivity() {
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

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        username = intent.getStringExtra(EXTRA_USERNAME) as String

        viewModel.getDetailUser(username).observe(this) { detailMovie ->
            populateContentDetail(detailMovie)
            showTitleCollapse("$username Profile")
        }

    }

    fun getData(): String = username

    private fun populateContentDetail(detailMovie: DetailUserResponse) {
        binding.apply {
            tvDetailName.text = detailMovie.name
            tvDetailUser.text = getString(R.string.username, detailMovie.login)
            tvDetailCompany.text = if (detailMovie.company == null) {
                getString(R.string.company, "-")
            } else {
                getString(R.string.company, detailMovie.company)
            }
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
                        this, R.color.white
                    )
                )
                isShow = true
            } else if (isShow) {
                binding.collapsingToolbar.title = " "
                isShow = false
            }
        })
    }


    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "extra_avatar"
    }
}
