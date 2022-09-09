package com.sally.tmbdreader.api.service

import com.sally.tmbdreader.api.ApiResult
import com.sally.tmbdreader.model.AccountData
import com.sally.tmbdreader.model.MovieData
import com.sally.tmbdreader.model.RequestTokenData
import com.sally.tmbdreader.model.SessionData
import retrofit2.http.*

interface AccountService {

    @GET("/3/authentication/token/new")
    suspend fun getRequestToken(): ApiResult<RequestTokenData>

    @POST("/3/authentication/token/validate_with_login")
    @FormUrlEncoded
    suspend fun login(
            @Field("username") userName: String,
            @Field("password") password: String,
            @Field("request_token") token: String
    ): ApiResult<RequestTokenData>

    @POST("/3/authentication/session/new")
    @FormUrlEncoded
    suspend fun createSession(
            @Field("request_token") token: String
    ): ApiResult<SessionData>

    @GET("/3/account")
    suspend fun getAccountDetail(
            @Query("session_id") sessionId: String
    ): ApiResult<AccountData>

    @GET("/3/account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int
    ): ApiResult<MovieData>

    @POST("/3/account/{account_id}/favorite")
    @FormUrlEncoded
    suspend fun setFavorite(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Field("media_type") type: String,
        @Field("media_id") id: Int,
        @Field("favorite") favorite: Boolean
    ): ApiResult<Any>
}