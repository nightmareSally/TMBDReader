package com.sally.tmbdreader.model

import com.google.gson.annotations.SerializedName

data class AccountData(
        val id: Int,
        val name: String,
        @SerializedName("username")
        val userName: String
)
