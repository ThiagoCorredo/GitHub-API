package com.tcorredo.githubapi.ui.gist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tcorredo.githubapi.R
import com.tcorredo.githubapi.databinding.FragmentGistBinding
import com.tcorredo.githubapi.ui.BaseFragment
import com.tcorredo.githubapi.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class GistFragment : BaseFragment<FragmentGistBinding>(FragmentGistBinding::inflate) {

    private val gistViewModel by viewModel<GistViewModel>()
    private var adapter = GistAdapter()
    private var isFirstTime = true
    private var isWaiting = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        gistViewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is GistViewState.Content -> {
                    binding.messageLayout.loadingProgress.visibility = View.GONE
                    if (binding.messageLayout.infoLayout.isVisible) {
                        binding.messageLayout.infoLayout.visibility = View.GONE
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.submitList(it.projects)
                    isWaiting = false
                }
                is GistViewState.EmptyState -> {
                    binding.messageLayout.loadingProgress.visibility = View.GONE
                    binding.messageLayout.infoLayout.visibility = View.VISIBLE
                    binding.messageLayout.infoIcon.setImageResource(R.drawable.ic_format_list_numbered_gray_72dp)
                    binding.messageLayout.infoText.setText(R.string.empty_list_repository)
                }
                is GistViewState.ShowError -> {
                    binding.messageLayout.loadingProgress.visibility = View.GONE
                    when (it.code) {
                        Constants.DEFAULT_VALUE -> {
                            if (binding.recyclerView.isVisible) {
                                binding.recyclerView.visibility = View.GONE
                            }
                            binding.messageLayout.loadingProgress.visibility = View.GONE
                            binding.messageLayout.infoLayout.visibility = View.VISIBLE
                            binding.messageLayout.infoIcon.setImageResource(R.drawable.ic_error_gray_72dp)
                            binding.messageLayout.infoText.text = it.message
                        }
                        Constants.FORBIDDEN_CODE -> {
                            Toast.makeText(
                                context,
                                R.string.limit_exceeded,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                is GistViewState.NoMorePage ->
                    Toast.makeText(
                        context,
                        R.string.no_more_pages,
                        Toast.LENGTH_LONG
                    ).show()
                is GistViewState.Loading ->
                    if (isFirstTime) {
                        isFirstTime = false
                        binding.messageLayout.loadingProgress.visibility = View.VISIBLE
                    }
            }
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !isWaiting) {
                    isWaiting = true
                    getGists()
                }
            }
        })
        getGists()
    }

    private fun getGists() = gistViewModel.getGists()
}