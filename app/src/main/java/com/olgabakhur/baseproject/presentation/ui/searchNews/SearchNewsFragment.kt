package com.olgabakhur.baseproject.presentation.ui.searchNews

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentSearchNewsBinding
import com.olgabakhur.baseproject.presentation.adapters.NewsAdapter
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectWhenStarted
import com.olgabakhur.baseproject.presentation.extensions.message
import com.olgabakhur.baseproject.presentation.util.Constants
import com.olgabakhur.baseproject.presentation.util.Constants.SEARCH_NEWS_TIME_DELAY
import com.olgabakhur.baseproject.presentation.util.view.Dialog
import com.olgabakhur.baseproject.presentation.util.viewModel.viewModel
import com.olgabakhur.data.util.result.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : BaseFragment(R.layout.fragment_search_news) {

    private val binding by viewBinding(FragmentSearchNewsBinding::bind)
    override val viewModel: SearchNewsViewModel by viewModel { App.appComponent.searchNewsViewModel }

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var mContext: Context

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        initRecyclerViewScrollListener()
        setupRecyclerView()
        setupRecyclerViewItemClickListener()
        setupSearchTextChangeListener()
    }

    //TODO: loading dialog
    override fun observeViewModel() {
        collectWhenStarted(viewModel.searchNewsResultFlow) { result ->
            // TODO: enable ui or adjust loading dialog
            when (result) {
                is Result.Success -> {
                    val newsItem = result.value
                    val newArticlesList = newsItem.articles.toList()
                    val totalPagesCount = newsItem.totalResults

                    newsAdapter.differList.submitList(newArticlesList)
                    val totalPages =
                        if (totalPagesCount == 0) 0 else totalPagesCount / Constants.QUERY_PAGE_SIZE + 2

                    isLastPage = viewModel.searchNewsPage == totalPages
                    if (isLastPage) {
                        binding.rvSearchNews.setPadding(0, 0, 0, 0)
                    }
                }

                is Result.Error -> {
                    //  TODO: binding.laySwipeToRefresh.isRefreshing = false
                    Dialog.showOkDialogWithTitle(
                        mContext,
                        R.string.general_error,
                        result.error.message(mContext)
                    )
                }
            }
        }
    }

    // TODO: Add Google paging
    private fun initRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                val isNotAtBeginning = firstVisibleItemPosition >= 0
                val isTotalMoreThenVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
                val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                        && isTotalMoreThenVisible && isScrolling

                if (shouldPaginate) {
                    viewModel.searchNews(binding.etSearch.text.toString())
                    isScrolling = false
                }
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }
    }

    private fun setupRecyclerViewItemClickListener() {
        newsAdapter.setOnItemClickListener {
            navigate(SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(it))
        }
    }

    private fun setupSearchTextChangeListener() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    val searchQuery = editable.toString()
                    if (searchQuery.isNotEmpty()) {
                        viewModel.searchNews(searchQuery)
                    }
                }
            }
        }
    }
}