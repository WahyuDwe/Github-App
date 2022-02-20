package com.wahyudwi.githubapp.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wahyudwi.githubapp.R
import com.wahyudwi.githubapp.data.model.SearchUser
import com.wahyudwi.githubapp.databinding.ItemUserBinding
import com.wahyudwi.githubapp.ui.detail.DetailActivity
import com.wahyudwi.githubapp.utils.DiffUtils

class MainAdapter : RecyclerView.Adapter<MainAdapter.UserViewHolder>() {
    private var oldListUser = emptyList<SearchUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemUserBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = oldListUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = oldListUser.size

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: SearchUser) {
            binding.apply {
                tvItemUser.text = user.login
                ivItemUser.load(user.avatar_url) {
                    placeholder(R.drawable.ic_placeholder)
                }
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ID, user.id)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                    intent.putExtra(DetailActivity.EXTRA_AVATAR, user.avatar_url)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }


    fun setData(newListUser: ArrayList<SearchUser>) {
        val diffUtil = DiffUtils(oldListUser, newListUser)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldListUser = newListUser
        diffResults.dispatchUpdatesTo(this)
    }
}