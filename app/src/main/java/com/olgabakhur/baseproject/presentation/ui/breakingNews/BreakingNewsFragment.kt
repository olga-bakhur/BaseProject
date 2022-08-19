package com.olgabakhur.baseproject.presentation.ui.breakingNews

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentBreakingNewsBinding
import com.olgabakhur.baseproject.presentation.adapters.NewsAdapter
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectLatestWhenStarted
import com.olgabakhur.baseproject.presentation.extensions.message
import com.olgabakhur.baseproject.presentation.util.view.Dialog
import com.olgabakhur.baseproject.presentation.util.viewModel.viewModel
import com.olgabakhur.data.util.result.Result

class BreakingNewsFragment : BaseFragment(R.layout.fragment_breaking_news) {

    override val binding by viewBinding(FragmentBreakingNewsBinding::bind)
    override val viewModel: BreakingNewsViewModel by viewModel { App.appComponent.breakingNewsViewModel }

    private val newsAdapter by lazy { NewsAdapter() }
    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        viewModel.getBreakingNews(countryCode = "us", pageNumber = 1)
        setupRecyclerView()
        setupRecyclerViewItemClickListener()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        collectLatestWhenStarted(viewModel.breakingNewsFlow) { result ->
            when (result) {
                is Result.Success -> {
                    val articlesList = result.value.articles.toList()
                    newsAdapter.differList.submitList(articlesList)
                }

                is Result.Error -> {
                    val error = result.error

                    if (error.isGenericError()) {
                        return@collectLatestWhenStarted
                    }

                    Dialog.showOkDialogWithTitle(
                        mContext,
                        R.string.general_error,
                        error.message(mContext)
                    )
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun setupRecyclerViewItemClickListener() {
        newsAdapter.setOnItemClickListener {
            navigate(
                BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it)
            )
        }
    }
}