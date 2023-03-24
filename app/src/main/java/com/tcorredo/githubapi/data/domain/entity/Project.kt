package com.tcorredo.githubapi.data.domain.entity

import androidx.recyclerview.widget.DiffUtil

data class Project(
    val id: Long,
    val fullName: String,
    val ownerName: String,
    val ownerAvatar: String,
    val name: String,
    val description: String?,
    val forks: Int,
    val starCount: Int,
    val licenseName: String?
) {
    companion object {
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem == newItem
            }
        }
    }
}
