package com.ngga_ring.dbmovie.api

import com.ngga_ring.dbmovie.models.Articles
import com.ngga_ring.dbmovie.models.BaseModels
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getNewsPage(
        @Query("pageSize") pageSize: String,
        @Query("page") page: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
    ): Response<BaseModels<List<Articles>>>

    @GET("top-headlines")
    suspend fun getNews(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
    ): Response<BaseModels<List<Articles>>>

    @GET("top-headlines")
    suspend fun getNewsSearc(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String,
    ): Response<BaseModels<List<Articles>>>

}