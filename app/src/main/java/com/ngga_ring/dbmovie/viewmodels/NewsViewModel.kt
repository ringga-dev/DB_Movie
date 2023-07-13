package com.ngga_ring.dbmovie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngga_ring.dbmovie.api.Resource
import com.ngga_ring.dbmovie.models.Articles
import com.ngga_ring.dbmovie.models.BaseModels
import com.ngga_ring.dbmovie.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val userRepository: NewsRepository,
) : ViewModel() {

    fun getNews(
        category: String,
        apiKey: String,
        pageSize: String?,
        page: String?,
        q: String = ""
    ): LiveData<Resource<BaseModels<List<Articles>>>> {
        val dataResult = MutableLiveData<Resource<BaseModels<List<Articles>>>>()
        dataResult.value = Resource.loading()

        viewModelScope.launch {
            try {
                val resource = userRepository.getNews(category, apiKey, pageSize, page,q)
                dataResult.value = resource
            } catch (e: Exception) {
                val errorMessage = e.localizedMessage ?: "Unknown error occurred"
                dataResult.value = Resource.error(errorMessage)
            }
        }
        return dataResult
    }
}