package com.sally.tmbdreader.api.service

import com.sally.tmbdreader.api.ApiResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountService {

    @POST("/account/{account_id}/favorite")
    @FormUrlEncoded
    fun setFavorite(
        @Path("account_id") accountId: String,
        @Field("media_type") type: String,
        @Field("media_id") id: Int,
        @Field("favorite") favorite: Boolean
    ): ApiResult<Any>
}