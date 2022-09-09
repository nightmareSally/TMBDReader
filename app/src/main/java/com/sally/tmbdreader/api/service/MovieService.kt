package com.sally.tmbdreader.api.service

import com.sally.tmbdreader.api.ApiResult
import com.sally.tmbdreader.model.MovieData
import com.sally.tmbdreader.model.RequestTokenData
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovie(@Query("page") page: Int): ApiResult<MovieData>
}