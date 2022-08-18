package com.olgabakhur.baseproject.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.olgabakhur.baseproject.databinding.ViewHolderArticleBinding
import com.olgabakhur.data.model.news.pojo.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }

    val differList = AsyncListDiffer(this, differCallback)

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
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
                Glide.with(context)
                    .load(article.urlToImage)
                    .into(binding.ivArticleImage)

                binding.tvSource.text = article.source?.name
                binding.tvTitle.text = article.title
                binding.tvDescription.text = article.description
                binding.tvPublishedAt.text = article.publishedAt

                setOnClickListener {
                    onItemClickListener?.let {
                        it(article)
                    }
                }
            }
        }
    }
}