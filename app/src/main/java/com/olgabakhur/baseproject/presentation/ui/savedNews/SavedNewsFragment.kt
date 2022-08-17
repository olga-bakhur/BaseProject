package com.olgabakhur.baseproject.presentation.ui.savedNews

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentSavedNewsBinding
import com.olgabakhur.baseproject.presentation.adapters.NewsAdapter
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectLatestWhenStarted
import com.olgabakhur.baseproject.presentation.extensions.message
import com.olgabakhur.baseproject.presentation.util.view.Dialog
import com.olgabakhur.baseproject.presentation.util.view.SnackbarAction
import com.olgabakhur.baseproject.presentation.util.view.showSnackbar
import com.olgabakhur.baseproject.presentation.util.view.showSnackbarWithAction
import com.olgabakhur.baseproject.presentation.util.viewModel.viewModel
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.flow.collectLatest

class SavedNewsFragment : BaseFragment(R.layout.fragment_saved_news) {

    private val binding by viewBinding(FragmentSavedNewsBinding::bind)
    override val viewModel: SavedNewsViewModel by viewModel { App.appComponent.savedNewsViewModel }

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var itemTouchHelperCallback: ItemTouchHelper.SimpleCallback
    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        viewModel.getSavedNews()
        setupRecyclerView()
        setupRecyclerViewItemClickListener()
        initNewsItemTouchHelperCallback()
        setupNewsItemTouchListener()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        collectLatestWhenStarted(viewModel.savedNewsResultFlow) { result ->
            // TODO: enable ui or adjust loading dialog
            when (result) {
                is Result.Success -> {
                    result.value.collectLatest { articlesList ->
                        newsAdapter.differList.submitList(articlesList)
                    }
                }

                is Result.Error -> {
                    Dialog.showOkDialogWithTitle(
                        mContext,
                        R.string.general_error,
                        result.error.message(mContext)
                    )
                }
            }
        }

        collectLatestWhenStarted(viewModel.deleteArticleResultFlow) { result ->
            // TODO: enable ui or adjust loading dialog
            when (result) {
                is Result.Success -> {
                    // TODO: restores at the end of the list, not previous position
                    showSnackbarWithAction(
                        mContext,
                        binding.root,
                        R.string.article_was_deleted_successfully_message,
                        Snackbar.LENGTH_LONG,
                        SnackbarAction(
                            viewModel::restoreArticle,
                            R.string.general_cancel
                        )
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

        collectLatestWhenStarted(viewModel.restoreArticleResultFlow) { result ->
            // TODO: enable ui or adjust loading dialog
            when (result) {
                is Result.Success -> {
                    showSnackbar(
                        mContext,
                        binding.root,
                        R.string.article_was_restored_successfully_message,
                        Snackbar.LENGTH_SHORT
                    )
                }

                is Result.Error -> {
                    Dialog.showOkDialogWithTitle(
                        mContext,
                        R.string.general_error,
                        R.string.article_restoration_failed_message
                    )
                }
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setupRecyclerViewItemClickListener() {
        newsAdapter.setOnItemClickListener {
            navigate(SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(it))
        }
    }

    private fun initNewsItemTouchHelperCallback() {
        itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article = newsAdapter.differList.currentList[position]
                viewModel.deleteArticle(article)
            }
        }
    }

    private fun setupNewsItemTouchListener() {
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }
}