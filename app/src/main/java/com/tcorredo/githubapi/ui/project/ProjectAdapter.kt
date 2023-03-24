package com.tcorredo.githubapi.ui.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tcorredo.githubapi.R
import com.tcorredo.githubapi.data.domain.entity.Project
import com.tcorredo.githubapi.databinding.ItemListRepositoryBinding
import com.tcorredo.githubapi.databinding.ItemLoadMoreBinding
import com.tcorredo.githubapi.util.ImageUtils

class ProjectAdapter :
    ListAdapter<Project, RecyclerView.ViewHolder>(Project.DIFF_UTIL_CALLBACK) {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = ItemListRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ItemViewHolder(binding)
        } else {
            val binding = ItemLoadMoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            LoadMoreViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bindView(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == currentList.size - 1) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    class ItemViewHolder(private val binding: ItemListRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(project: Project) {
            binding.run {
                repositoryTitle.text = project.name
                repositoryForkNumber.text = project.forks.toString()
                repositoryStarNumber.text = project.starCount.toString()
                repositoryDescription.text = project.description
                ImageUtils.createRoundImage(userProfileImage, project.ownerAvatar)
                repositoryUserName.text = project.ownerName
                repositoryLicense.text = project.licenseName
                    ?: repositoryLicense.context.getText(R.string.repository_without_license)
            }
        }
    }

    class LoadMoreViewHolder(binding: ItemLoadMoreBinding) :
        RecyclerView.ViewHolder(binding.root)
}
