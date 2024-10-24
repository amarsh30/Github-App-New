package com.githubapp.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.githubapp.R
import com.githubapp.data.local.entity.UserFavoriteEntity
import com.githubapp.databinding.ActivityDetailBinding
import com.githubapp.ui.ViewModelFactory
import com.githubapp.ui.adapter.SectionPagerAdapter
import com.githubapp.ui.favorite.FavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var userState: Boolean = false
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val VMFavorite: FavoriteViewModel by viewModels(){
        ViewModelFactory.getInstance(application)
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login = intent.getStringExtra(data).toString()

        viewModel.getDetail(login)
        viewModel.username.observe(this) {
            binding.apply {
                tvUsernameDetail.text = it.login
                if (it.name.isNullOrEmpty()) {
                    tvNameDetail.text = getString(R.string.Name_NotAvailable)
                } else {
                    tvNameDetail.text = it.name
                }

                tvFollowersDetail.text = "${it.followers} Followers"
                tvFollowingDetail.text = "${it.following} Following"
            }
            Glide.with(this)
                .load(it.avatarUrl)
                .into(binding.ivPhotoDetail)
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this, login)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabsLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val imgUrl = intent.getStringExtra(img)
        setFab(login)
        binding.fabFav.setOnClickListener {
            val favorite = UserFavoriteEntity(login, imgUrl.toString())
            if (userState) {
                VMFavorite.deleteUser(favorite)
            } else {
                VMFavorite.insertUser(favorite)
            }
        }

        val callback = supportActionBar
        callback?.title = "Detail Github User"
        callback?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setFab(login: String) {
        VMFavorite.getFavoriteUser(login).observe(this) {
            if (it != null) {
                userState = true
                binding.fabFav.setImageResource(R.drawable.ic_favorited_24)
            } else {
                userState = false
                binding.fabFav.setImageResource(R.drawable.ic_favorite_24)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        var login = ""
        const val data = "DATA"
        const val img = "IMG"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}