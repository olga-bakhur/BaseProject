package com.olgabakhur.baseproject.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.ViewHolderArticleBinding
import com.olgabakhur.baseproject.presentation.extensions.getTextOrNoInfoMessage
import com.olgabakhur.data.model.dto.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.urlToArticle == newItem.urlToArticle

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }

    val differList = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderArticleBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(
            binding = binding
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) =
        holder.bind(differList.currentList[position])

    override fun getItemCount(): Int = differList.currentList.size


    inner class ArticleViewHolder(
        private val binding: ViewHolderArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            setupTitle(article)
            setupSource(article)
            setupArticleImage(article)
            setupContent(article)
            setupPublicationDate(article)
        }

        private fun setupTitle(article: Article) {
            binding.tvTitle.apply {
                text = context.getTextOrNoInfoMessage(article.title)
                isSelected = true
            }
        }

        private fun setupSource(article: Article) {
            binding.tvSource.apply {
                text = context.getTextOrNoInfoMessage(article.sourceName)
                isSelected = true
            }
        }

        private fun setupContent(article: Article) {
            binding.tvContent.apply {
                text = context.getTextOrNoInfoMessage(article.content)
            }
        }

        private fun setupPublicationDate(article: Article) {
            binding.tvPublicationDate.apply {
                text = context.getTextOrNoInfoMessage(article.publicationDate)
                isSelected = true
            }
        }

        private fun setupArticleImage(article: Article) {
            Glide.with(binding.ivArticleImage.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_article_image_placeholder)
                .error(R.drawable.ic_article_image_error)
                .into(binding.ivArticleImage)
        }
    }
}