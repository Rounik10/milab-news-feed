package com.rounik.milab.newsfeed.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rounik.milab.newsfeed.R
import com.rounik.milab.newsfeed.databinding.NewsItemBinding
import com.rounik.milab.newsfeed.model.News

class NewsAdapter(private var newsList: List<News>, private val listener: NewsSelectedListener) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context))
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.binding.apply {
            newsTitle.text = currentItem.title.toString()
            Glide.with(newsImage.context)
                .load(currentItem.urlToImage)
                .placeholder(R.drawable.ic_news)
                .into(newsImage)
            root.setOnClickListener { listener.onNewsSelected(currentItem) }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<News>) {
        this.newsList = newList
        notifyDataSetChanged()
    }
}

interface NewsSelectedListener {
    fun onNewsSelected(news: News)
}
