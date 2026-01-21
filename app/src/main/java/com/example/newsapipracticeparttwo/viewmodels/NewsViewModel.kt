package com.example.newsapipracticeparttwo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapipracticeparttwo.model.ArticleEntity
import com.example.newsapipracticeparttwo.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val currentDbLimiter = MutableStateFlow(20)

    @OptIn(ExperimentalCoroutinesApi::class)
    val articles : StateFlow<List<ArticleEntity>> = currentDbLimiter
        .flatMapLatest { limit ->
            newsRepository.getArticlesFromDatabase(limit)
        } .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private var currentPage = 1
    private var isLoading = false
    var canLoadMore = true

    init{
        fetchNews()
    }

    fun loadMore(){
        currentDbLimiter.value += 20
        fetchNews()
    }

    fun fetchNews(){
        if(isLoading || !canLoadMore) return

        isLoading = true


        try {
            viewModelScope.launch{
                newsRepository.getNewsFromApi(currentPage)
                currentPage++
            }
        } catch (e : Exception){
            e.printStackTrace()
        } finally {
            isLoading = false
        }

    }

}