package com.tcorredo.githubapi.ui.project

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tcorredo.githubapi.R
import com.tcorredo.githubapi.databinding.FragmentProjectBinding
import com.tcorredo.githubapi.ui.BaseFragment
import com.tcorredo.githubapi.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProjectFragment : BaseFragment<FragmentProjectBinding>(FragmentProjectBinding::inflate) {

    private val projectViewModel by viewModel<ProjectViewModel>()
    private var adapter = ProjectAdapter()
    private var isFirstTime = true
    private var isWaiting = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = linearLayoutManager

        projectViewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is ProjectViewState.Content -> {
                    binding.messageLayout.loadingProgress.visibility = View.GONE
                    if (binding.messageLayout.infoLayout.isVisible) {
                        binding.messageLayout.infoLayout.visibility = View.GONE
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.submitList(it.projects)
                    isWaiting = false
                }
                is ProjectViewState.EmptyState -> {
                    binding.messageLayout.loadingProgress.visibility = View.GONE
                    binding.messageLayout.infoLayout.visibility = View.VISIBLE
                    binding.messageLayout.infoIcon.setImageResource(R.drawable.ic_format_list_numbered_gray_72dp)
                    binding.messageLayout.infoText.setText(R.string.empty_list_repository)
                }
                is ProjectViewState.ShowError -> {
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
                is ProjectViewState.NoMorePage ->
                    Snackbar.make(
                        binding.frameLayout,
                        R.string.no_more_pages,
                        Snackbar.LENGTH_SHORT
                    ).show()
                is ProjectViewState.Loading ->
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
                    getProjects()
                }
            }
        })
        getProjects()
    }

    private fun getProjects() = projectViewModel.getProjects()
}