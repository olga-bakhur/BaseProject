package com.olgabakhur.baseproject.presentation.ui.breakingNews

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentBreakingNewsBinding
import com.olgabakhur.baseproject.presentation.adapters.NewsAdapter
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectLatestWhenStarted
import com.olgabakhur.baseproject.presentation.extensions.message
import com.olgabakhur.baseproject.presentation.util.Constants.QUERY_PAGE_SIZE
import com.olgabakhur.baseproject.presentation.util.view.Dialog
import com.olgabakhur.baseproject.presentation.util.viewModelUtil.viewModel
import com.olgabakhur.domain.util.result.Result

class BreakingNewsFragment : BaseFragment(R.layout.fragment_breaking_news) {

    private val binding by viewBinding(FragmentBreakingNewsBinding::bind)
    override val viewModel: BreakingNewsViewModel by viewModel { App.appComponent.breakingNewsViewModel }

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var mContext: Context

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        viewModel.getBreakingNews(countryCode = "us")
        setupSwipeToRefresh()
        initRecyclerViewScrollListener()
        setupRecyclerView()
        setupRecyclerViewItemClickListener()
    }

    override fun observeViewModel() {
        collectLatestWhenStarted(viewModel.breakingNewsResultFlow) { result ->
            // TODO: enable ui or adjust loading dialog
            when (result) {
                is Result.Success -> {
                    val newsItem = result.value
                    val newArticlesList = newsItem.articles.toList()
                    val totalPagesCount = newsItem.totalResults

                    newsAdapter.differList.submitList(newArticlesList)
                    val totalPages =
                        if (totalPagesCount == 0) 0 else totalPagesCount / QUERY_PAGE_SIZE + 2

                    isLastPage = viewModel.breakingNewsPage == totalPages
                    if (isLastPage) {
                        binding.rvBreakingNews.setPadding(0, 0, 0, 0)
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

    private fun setupSwipeToRefresh() {
        binding.laySwipeToRefresh.apply {
            setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary)
            setOnRefreshListener {
                viewModel.getBreakingNews(countryCode = "us")
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
                val isTotalMoreThenVisible = totalItemCount >= QUERY_PAGE_SIZE
                val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                        && isTotalMoreThenVisible && isScrolling

                if (shouldPaginate) {
                    viewModel.getBreakingNews("us")
                    isScrolling = false
                }
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    private fun setupRecyclerViewItemClickListener() {
        newsAdapter.setOnItemClickListener {
            navigate(BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it))
        }
    }
}