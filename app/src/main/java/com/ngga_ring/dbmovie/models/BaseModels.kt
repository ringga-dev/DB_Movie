package com.ngga_ring.dbmovie.models


data class BaseModels<T>(
    val status: String,
    val code: String,
    val message: String,
    val totalResults: String,
    val articles: T
)
