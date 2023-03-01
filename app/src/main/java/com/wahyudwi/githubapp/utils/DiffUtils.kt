package com.wahyudwi.githubapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.wahyudwi.githubapp.data.model.SearchUser

class DiffUtils(
    private val oldList: List<SearchUser>,
    private val newList: List<SearchUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }

            oldList[oldItemPosition].login != newList[newItemPosition].login -> {
                false
            }

            oldList[oldItemPosition].avatarUrl != newList[newItemPosition].avatarUrl -> {
                false
            }
            else -> true
        }
    }

}