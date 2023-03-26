package com.tcorredo.githubapi.ui.gist

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tcorredo.githubapi.data.domain.entity.Gist
import com.tcorredo.githubapi.databinding.ItemListGistBinding
import com.tcorredo.githubapi.databinding.ItemLoadMoreBinding
import com.tcorredo.githubapi.util.ImageUtils
import java.util.*

class GistAdapter :
    ListAdapter<Gist, RecyclerView.ViewHolder>(Gist.DIFF_UTIL_CALLBACK) {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = ItemListGistBinding.inflate(
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

    class ItemViewHolder(private val binding: ItemListGistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(gist: Gist) {
            binding.run {
                ImageUtils.createRoundImage(userProfileImage, gist.ownerAvatar)
                gistUserName.text = gist.ownerName
                gistTitle.text = gist.files.joinToString(separator = ", ")
                gistCreatedTime.text = DateUtils.getRelativeTimeSpanString(
                    gist.createAt.time,
                    Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS
                )
                gistFilesNumber.text = gist.files.size.toString()
                gistCommentsNumber.text = gist.comments.toString()
            }
        }
    }

    class LoadMoreViewHolder(binding: ItemLoadMoreBinding) :
        RecyclerView.ViewHolder(binding.root)
}