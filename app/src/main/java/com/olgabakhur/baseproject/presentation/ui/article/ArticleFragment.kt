package com.olgabakhur.baseproject.presentation.ui.article

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.global.di.App
import com.olgabakhur.data.model.news.Article
import com.olgabakhur.baseproject.databinding.FragmentArticleBinding
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.viewModel

class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    private val binding by viewBinding(FragmentArticleBinding::bind)
    override val viewModel: ArticleViewModel by viewModel { App.appComponent.articleNewsViewModel }

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var article: Article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArgs()
        setupFabClickListener()
    }

    private fun setupArgs() {
        article = args.article // TODO: getById()

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
    }

    private fun setupFabClickListener() {
        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            // TODO: move snack bar creation to extensions
            Snackbar.make(
                binding.root,
                getString(R.string.article_saved_saved_successfully_message),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}