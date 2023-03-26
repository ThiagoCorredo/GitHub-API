package com.tcorredo.githubapi.data.domain.entity

import androidx.recyclerview.widget.DiffUtil
import java.util.Date

data class Gist(
    val id: String,
    val createAt: Date,
    val files: List<String>,
    val comments: Int,
    val ownerName: String,
    val ownerAvatar: String,
) {
    companion object {
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Gist>() {
            override fun areItemsTheSame(oldItem: Gist, newItem: Gist): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Gist, newItem: Gist): Boolean {
                return oldItem == newItem
            }
        }
    }
}
