package com.ngga_ring.dbmovie.repository

import com.ngga_ring.dbmovie.api.ApiService
import com.ngga_ring.dbmovie.api.Resource
import com.ngga_ring.dbmovie.models.Articles
import com.ngga_ring.dbmovie.models.BaseModels
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getNews(
        category: String,
        apiKey: String,
        pageSize: String? = null,
        page: String? = null,
        q: String? = ""
    ): Resource<BaseModels<List<Articles>>> {
        return try {
            val response =
                if (page != null && pageSize != null) apiService.getNewsPage(
                    pageSize,
                    page,
                    category,
                    apiKey,
                ) else if (category == "" && q.toString() != "") apiService.getNewsSearc(
                    q.toString(),
                    apiKey
                ) else apiService.getNews(category, apiKey)
            if (response.isSuccessful) {
                val userResponse = response.body()
                Resource.success(userResponse)
            } else {
                Resource.error("Login failed")
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage ?: "Unknown error occurred")
        }
    }


}