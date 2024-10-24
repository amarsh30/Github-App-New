package com.githubapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.githubapp.data.remote.response.UserResponse
import com.githubapp.databinding.ItemDataUserBinding
import com.githubapp.ui.detail.DetailActivity

class RecyclerViewAdapter (private val listUser: List<UserResponse>) :
    RecyclerView.Adapter<RecyclerViewAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemDataUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val (avatarUrl, username) = listUser[position]
        holder.binding.tvItemName.text = username
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .into(holder.binding.imgItemPhoto)


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.data, username)
            intent.putExtra(DetailActivity.img, avatarUrl)
            Toast.makeText(holder.itemView.context, username, Toast.LENGTH_SHORT)
                .show()
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class UserViewHolder(var binding: ItemDataUserBinding) : RecyclerView.ViewHolder(binding.root)

}