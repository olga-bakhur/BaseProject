package com.olgabakhur.baseproject.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.ViewHolderArticleBinding
import com.olgabakhur.baseproject.presentation.extensions.setTextOrNoInfoLabel
import com.olgabakhur.baseproject.presentation.util.onClickListener.setOnCLick
import com.olgabakhur.baseproject.presentation.util.view.gone
import com.olgabakhur.baseproject.presentation.util.view.visible
import com.olgabakhur.data.model.dto.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }

    val differList = AsyncListDiffer(this, differCallback)

    private var saveOrDeleteArticle: ((Article) -> Unit)? = null

    fun setOnSaveOrDeleteArticleClickListener(listener: (Article) -> Unit) {
        saveOrDeleteArticle = listener
    }

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
            with(itemView) {
                setupTitle(article)
                setupSource(article)
                setupArticleImage(article, context)
                setupDescription(article)
                setupContent(article)
                setupPublishedAt(article)
                setupButtonSave(article, context)
                setupOnItemClickListener(itemView)
            }
        }

        private fun setupTitle(article: Article) {
            binding.tvTitle.apply {
                text = context.setTextOrNoInfoLabel(article.title)
                isSelected = true
            }
        }

        private fun setupSource(article: Article) {
            binding.tvSource.apply {
                text = context.setTextOrNoInfoLabel(article.sourceName)
                isSelected = true
            }
        }

        private fun setupArticleImage(article: Article, context: Context) {
            Glide.with(context)
                .load(article.urlToImage)
                .centerCrop() // TODO: ??
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_news_image_preloader)
                        .error(R.drawable.ic_news_image_error)
                        .fitCenter()
                )
                .into(binding.ivArticleImage)
        }

        private fun setupDescription(article: Article) {
            binding.tvDescription.apply {
                text = context.setTextOrNoInfoLabel(article.description)
            }
        }

        private fun setupContent(article: Article) { // TODO: transition
            binding.tvContent.apply {
                text = context.setTextOrNoInfoLabel(article.content)
            }
        }

        private fun setupPublishedAt(article: Article) {
            binding.tvPublishedAt.apply {
                text = article.publishedAt
                isSelected = true
            }
        }

        private fun setupButtonSave(article: Article, context: Context) {
            // TODO: add selector??
            if (article.isSaved) {
                binding.ivSave.setColorFilter(
                    context.resources.getColor(
                        R.color.colorSecondary,
                        null
                    )
                )
            } else {
                binding.ivSave.clearColorFilter()
            }

            binding.ivSave.setOnCLick {
                saveOrDeleteArticle?.let {
                    it(article)
                }

                updateItem()
            }
        }

        private fun setupOnItemClickListener(itemView: View) {
            itemView.setOnClickListener {
                setupTransition()
            }
        }

        private fun updateItem() { // TODO: update doesn't work
            bindingAdapter?.notifyItemChanged(bindingAdapterPosition)
        }

        private fun setupTransition() { // TODO: create transition
            val isContentVisible = binding.tvContent.isVisible
            if (isContentVisible) {
                binding.tvContent.gone()
            } else {
                binding.tvContent.visible()
            }
        }
    }
}