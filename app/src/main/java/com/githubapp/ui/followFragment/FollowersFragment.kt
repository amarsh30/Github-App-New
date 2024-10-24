package com.githubapp.ui.followFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubapp.data.remote.response.UserResponse
import com.githubapp.databinding.FragmentFollowersBinding
import com.githubapp.ui.adapter.RecyclerViewAdapter
import com.githubapp.ui.detail.DetailViewModel

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var rvUser: RecyclerView

    private var position: Int = 1
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUser = binding.rvFollowers
        rvUser.setHasFixedSize(true)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1) {
            viewModel.getFollowers(username ?: "")
        } else {
            viewModel.getFollowing(username ?: "")
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.apply {
            rvFollowers.layoutManager = layoutManager
            rvFollowers.addItemDecoration(itemDecoration)
        }

        viewModel.listFollowers.observe(viewLifecycleOwner) { listUsers ->
            showRecyclerList(listUsers)
            checkDataUsers(listUsers)

        }

        viewModel.listFollowing.observe(viewLifecycleOwner) { lisUsers ->
            showRecyclerList(lisUsers)
            checkDataUsers(lisUsers)

        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun checkDataUsers(list: List<UserResponse>) {
        if (list.isEmpty()) {
            binding.apply {
                ivNotFound.visibility = View.VISIBLE
                tvNotFound.visibility = View.VISIBLE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRecyclerList(list: List<UserResponse>) {
        rvUser.layoutManager = LinearLayoutManager(context)
        val adapter = RecyclerViewAdapter(list)
        rvUser.adapter = adapter
    }

    companion object {
        const val ARG_USERNAME = "arg_username"
        const val ARG_POSITION = "arg_position"
    }
}