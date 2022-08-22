package com.olgabakhur.baseproject.presentation.ui.breakingNews

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentBreakingNewsBinding
import com.olgabakhur.baseproject.presentation.adapters.NewsAdapter
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectLatestWhenStarted
import com.olgabakhur.baseproject.presentation.extensions.collectWhenStarted
import com.olgabakhur.baseproject.presentation.extensions.message
import com.olgabakhur.baseproject.presentation.util.view.Dialog
import com.olgabakhur.baseproject.presentation.util.view.showSnackbar
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
        setupRecyclerView()
        setupButtonSaveClickListener()
    }

    override fun observeViewModel() {
        super.observeViewModel()

        // 1. Get all articles
        collectLatestWhenStarted(
            viewModel.getBreakingNewsWithIsSavedInfo(
                countryCode = "us",
                pageNumber = 1
            )
        ) { result ->
            when (result) {
                is Result.Success -> {
                    Log.d("TAGGG", "Combined Success")
                    newsAdapter.differList.submitList(result.value)
                }

                is Result.Error -> {
                    Log.d("TAGGG", "Combined Error")
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

        collectLatestWhenStarted(viewModel.errorFlow) { error ->
            Dialog.showOkDialogWithTitle(
                mContext,
                R.string.general_error,
                error.message(mContext)
            )
        }

        // 2. Save an article
        collectWhenStarted(viewModel.saveArticleFlow) { result ->
            when (result) {
                is Result.Success -> {
                    showSnackbar(
                        mContext,
                        binding.root,
                        R.string.article_was_saved_successfully_message,
                        Snackbar.LENGTH_SHORT
                    )
                }

                is Result.Error -> {
                    Dialog.showOkDialogWithTitle(
                        mContext,
                        R.string.general_error,
                        R.string.article_saving_failed_message
                    )
                }
            }
        }

        // 3. Delete an article
        collectWhenStarted(viewModel.deleteArticleFlow) { result ->
            when (result) {
                is Result.Success -> {
                    showSnackbar(
                        mContext,
                        binding.root,
                        R.string.article_was_deleted_successfully_message
                    )
                }

                is Result.Error -> {
                    Dialog.showOkDialogWithTitle(
                        mContext,
                        R.string.general_error,
                        R.string.article_deletion_failed_message
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

    private fun setupButtonSaveClickListener() {
        newsAdapter.setOnSaveOrDeleteArticleClickListener { article ->
            if (article.isSaved) {
                viewModel.deleteArticle(article)
            } else {
                viewModel.saveArticle(article)
            }
        }
    }
}