package com.olgabakhur.baseproject.presentation.ui.articles

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentArticlesBinding
import com.olgabakhur.baseproject.presentation.adapters.ArticleAdapter
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectLatestWhenStarted
import com.olgabakhur.baseproject.presentation.util.viewmodel.viewModel
import com.olgabakhur.data.util.result.Result

class ArticlesFragment : BaseFragment(R.layout.fragment_articles) {

    override val binding by viewBinding(FragmentArticlesBinding::bind)
    override val viewModel: ArticleViewModel by viewModel { App.appComponent.articleViewModel }

    private val articleAdapter by lazy { ArticleAdapter() }
    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        setupRecyclerView()
        viewModel.getArticlesList(
            countryCode = "us",
            pageNumber = 1
        )
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewLifecycleOwner.collectLatestWhenStarted(viewModel.articlesFlow) { result ->
            if (result is Result.Success) {
                articleAdapter.differList.submitList(result.value)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvBreakingNews.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }
}