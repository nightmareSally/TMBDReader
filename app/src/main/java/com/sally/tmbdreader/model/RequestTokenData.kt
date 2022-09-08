package com.sally.tmbdreader.model

import com.google.gson.annotations.SerializedName

data class RequestTokenData(
        @SerializedName("expires_at")
        val expire: String,
        @SerializedName("request_token")
        val token: String
)