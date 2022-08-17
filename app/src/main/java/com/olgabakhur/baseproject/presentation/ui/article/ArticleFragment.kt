package com.olgabakhur.baseproject.presentation.ui.article

import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentArticleBinding
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectLatestWhenStarted
import com.olgabakhur.baseproject.presentation.util.view.Dialog
import com.olgabakhur.baseproject.presentation.util.view.showSnackbar
import com.olgabakhur.baseproject.presentation.util.viewModel.viewModel
import com.olgabakhur.data.model.news.Article
import com.olgabakhur.data.util.result.Result

class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    private val binding by viewBinding(FragmentArticleBinding::bind)
    override val viewModel: ArticleViewModel by viewModel { App.appComponent.articleNewsViewModel }

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var mContext: Context
    private lateinit var article: Article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        setupArgs()
        setupWebView()
        setupFabClickListener()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        collectLatestWhenStarted(viewModel.saveArticleResultFlow) { result ->
            // TODO: enable ui or adjust loading dialog
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
    }

    private fun setupArgs() {
        article = args.article // TODO: getById()
    }

    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

    private fun setupFabClickListener() {
        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
        }
    }
}