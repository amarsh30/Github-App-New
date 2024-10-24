package com.githubapp.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubapp.data.remote.response.UserResponse
import com.githubapp.databinding.ActivityFavoriteBinding
import com.githubapp.ui.ViewModelFactory
import com.githubapp.ui.adapter.RecyclerViewAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        viewModel.getAllUsers().observe(this) {
            val listUsers = ArrayList<UserResponse>()
            if (it != null) {
                for (user in it) {
                    val users = UserResponse(
                        user.avatarUrl,
                        user.login,
                        0,
                        0,
                        "")
                    listUsers.add(users)
                }
            }
            binding.rvFavorite.adapter = RecyclerViewAdapter(listUsers)
            checkDataUsers(listUsers)

        }

        val callback = supportActionBar
        callback?.title = "Favorite Github User"
        callback?.setDisplayHomeAsUpEnabled(true)
    }

    private fun checkDataUsers(list: ArrayList<UserResponse>) {
        if (list.isEmpty()) {
            binding.apply {
                ivNotFound.visibility = View.VISIBLE
                tvNotFound.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}