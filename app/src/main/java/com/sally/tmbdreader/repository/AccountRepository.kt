package com.sally.tmbdreader.repository

import com.sally.tmbdreader.api.service.AccountService
import retrofit2.http.Field

class AccountRepository(private val service: AccountService) {
    suspend fun getRequestToken() = service.getRequestToken()
    suspend fun login(userName: String, password: String, token: String) = service.login(userName, password, token)
    suspend fun createSession(token: String) = service.createSession(token)
    suspend fun getAccountDetail(sessionId: String) = service.getAccountDetail(sessionId)
    suspend fun setFavorite(accountId: String, type: String, id: Int, favorite: Boolean) = service.setFavorite(accountId, type, id, favorite)
}