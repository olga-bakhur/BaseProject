package com.olgabakhur.baseproject.presentation.ui.savedNews

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
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
import com.olgabakhur.baseproject.presentation.extensions.collectLatestLifecycleFlow
import com.olgabakhur.baseproject.presentation.util.viewModelUtil.viewModel
import com.olgabakhur.data.model.news.Article

class SavedNewsFragment : BaseFragment(R.layout.fragment_saved_news) {

    private val binding by viewBinding(FragmentSavedNewsBinding::bind)
    override val viewModel: SavedNewsViewModel by viewModel { App.appComponent.savedNewsViewModel }

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var itemTouchHelperCallback: ItemTouchHelper.SimpleCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRecyclerViewItemClickListener()
        initNewsItemTouchHelperCallback()
        setupNewsItemTouchListener()
    }

    override fun observeViewModel() {
        super.observeViewModel()

        collectLatestLifecycleFlow(viewModel.getSavedNews()) { articlesList: List<Article> ->
            newsAdapter.differList.submitList(articlesList)
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
            findNavController().navigate(
                SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(
                    it
                )
            )
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

                Snackbar.make(binding.root, "Successfully deleted article", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
            }
        }
    }

    private fun setupNewsItemTouchListener() {
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }
}