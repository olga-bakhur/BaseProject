package com.olgabakhur.baseproject.presentation.ui.breakingnews

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
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
import com.olgabakhur.baseproject.presentation.ui.MainActivity
import com.olgabakhur.baseproject.presentation.util.view.Dialog
import com.olgabakhur.baseproject.presentation.util.view.showSnackbar
import com.olgabakhur.baseproject.presentation.util.viewmodel.viewModel
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

    override fun setupToolbarMenu() {
        super.setupToolbarMenu()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.findItem(R.id.actionFilter).isVisible = true
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                when (menuItem.itemId) {
                    R.id.showBreakingNews -> {
                        showNewsByCategory(NewsCategory.BREAKING_NEWS)
                        true
                    }

                    R.id.showSavedNews -> {
                        showNewsByCategory(NewsCategory.SAVED_NEWS)
                        true
                    }
                    else -> false
                }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun observeViewModel() {
        super.observeViewModel()

        // 1. Get all articles
        // TODO: Doesn't receive updates for getSavedArticles()
        //  after inserting / deletion of a single article, therefore the "Flag" icon color is not changing.
        collectLatestWhenStarted(
            viewModel.getBreakingNewsWithIsSavedInfo(
                countryCode = "us",
                pageNumber = 1
            )
        ) { result ->
            when (result) {
                is Result.Success -> {
                    if (viewModel.currentNewsCategory == NewsCategory.BREAKING_NEWS) {
                        newsAdapter.differList.submitList(result.value)
                    }
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

    private fun showNewsByCategory(category: NewsCategory) {
        val filteredList = viewModel.getNewsByCategory(category)
        newsAdapter.differList.submitList(null)
        newsAdapter.differList.submitList(filteredList)

        (requireActivity() as MainActivity).setToolbarTitle(
            if (category == NewsCategory.BREAKING_NEWS) {
                R.string.breaking_news_label
            } else {
                R.string.saved_news_label
            }
        )
    }
}