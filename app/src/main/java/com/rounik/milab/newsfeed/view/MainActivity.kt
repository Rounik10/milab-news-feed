package com.rounik.milab.newsfeed.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.rounik.milab.newsfeed.adapters.NewsAdapter
import com.rounik.milab.newsfeed.adapters.NewsSelectedListener
import com.rounik.milab.newsfeed.databinding.ActivityMainBinding
import com.rounik.milab.newsfeed.model.News
import com.rounik.milab.newsfeed.viewmodel.MainViewModel


class MainActivity : AppCompatActivity(), NewsSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
    }

    private val newsList: List<News> = mutableListOf()

    private fun setupRecyclerView() {
        binding.newsRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = NewsAdapter(newsList, this@MainActivity)
        }

        viewModel.errorState.observe(this) { handleError(it) }
        viewModel.loadingState.observe(this) { toggleLoadingState(it) }
        viewModel.postList.observe(this) { updateUI(it) }
        viewModel.fetchNews()
    }

    private fun handleError(error: Boolean) {
        if (error) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleLoadingState(loading: Boolean) {
        binding.progressCircular.isVisible = loading
    }

    private fun updateUI(newsList: List<News>) {
        (binding.newsRecycler.adapter as? NewsAdapter)?.run { updateData(newsList) }
    }

    override fun onNewsSelected(news: News) {
        val url = news.url
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}