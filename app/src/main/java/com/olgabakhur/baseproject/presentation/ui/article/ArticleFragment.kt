package com.olgabakhur.baseproject.presentation.ui.article

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentArticleBinding
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectWhenStarted
import com.olgabakhur.baseproject.presentation.util.view.Dialog
import com.olgabakhur.baseproject.presentation.util.view.showSnackbar
import com.olgabakhur.baseproject.presentation.util.viewModel.viewModel
import com.olgabakhur.data.util.result.Result

class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    override val binding by viewBinding(FragmentArticleBinding::bind)
    override val viewModel: ArticleViewModel by viewModel { App.appComponent.articleNewsViewModel }

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var mContext: Context
    private lateinit var webClient: WebViewClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        setupArgs()
        setupFabClickListener()
        initWebViewClient()
        setupWebView()
    }

    override fun observeViewModel() {
        super.observeViewModel()
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
    }

    private fun setupArgs() {
        viewModel.setArticle(args.article)
    }

    private fun setupFabClickListener() {
        binding.fab.setOnClickListener { viewModel.saveArticle() }
    }

    private fun initWebViewClient() {
        webClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showLoading(true)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                showLoading(false)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                showLoading(false)

                Dialog.showOkDialogWithTitle(
                    requireContext(),
                    R.string.general_error,
                    error?.description?.toString() ?: getString(R.string.error_generic)
                )
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = webClient
            settings.javaScriptEnabled = true
            viewModel.article?.url?.let { url ->
                loadUrl(url)
            }
            settings.setSupportMultipleWindows(true)
        }
    }
}