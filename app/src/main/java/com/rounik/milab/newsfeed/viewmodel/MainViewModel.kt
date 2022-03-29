package com.rounik.milab.newsfeed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rounik.milab.newsfeed.api.NewsService
import com.rounik.milab.newsfeed.api.RetrofitInstance
import com.rounik.milab.newsfeed.model.News
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val newsService: NewsService = RetrofitInstance.api

    private val _loadingState = MutableLiveData(false)
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState = MutableLiveData(false)
    val errorState: LiveData<Boolean> get() = _errorState

    private val _postList = MutableLiveData<List<News>>(mutableListOf())
    val postList: LiveData<List<News>> get() = _postList

    fun fetchNews() {
        _loadingState.value = true
        viewModelScope.launch {
            val response = newsService.getHeadlines()
            _loadingState.value = false
            if (response.status.equals("ok", ignoreCase = true)) {
                _postList.value = response.articles
            } else {
                _errorState.value = true
            }
        }
    }

}