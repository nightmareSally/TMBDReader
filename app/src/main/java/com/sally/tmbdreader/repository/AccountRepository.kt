package com.sally.tmbdreader.repository

import com.sally.tmbdreader.api.ApiResult
import com.sally.tmbdreader.api.service.AccountService
import com.sally.tmbdreader.db.dao.FavoriteDao
import com.sally.tmbdreader.db.entity.FavoriteEntity
import com.sally.tmbdreader.model.MovieData
import com.sally.tmbdreader.util.Extensions.onSuccess

class AccountRepository(private val service: AccountService, private val dao: FavoriteDao) {
    suspend fun getRequestToken() = service.getRequestToken()
    suspend fun login(userName: String, password: String, token: String) =
        service.login(userName, password, token)

    suspend fun createSession(token: String) = service.createSession(token)
    suspend fun getAccountDetail(sessionId: String) = service.getAccountDetail(sessionId)
    suspend fun getFavoriteMovies(
        accountId: Int,
        sessionId: String,
        page: Int
    ): ApiResult<MovieData> {
        return service.getFavoriteMovies(accountId, sessionId, page)
            .onSuccess { movieData ->
                movieData.results?.let { movies ->
                    dao.insertAll(movies.map { FavoriteEntity(it.movieId) })
                }
            }
    }

    suspend fun setFavorite(
        accountId: Int,
        sessionId: String,
        id: Int,
        favorite: Boolean
    ): ApiResult<Any> {
        return service.setFavorite(accountId, sessionId, "movie", id, favorite)
            .onSuccess {
                if (favorite) {
                    dao.insert(FavoriteEntity(id))
                } else {
                    dao.delete(FavoriteEntity(id))
                }
            }
    }

    fun checkIsFavorite(id: Int): Boolean {
        return dao.getFavoriteCount(id) != 0
    }

    suspend fun clearFavorite() {
        dao.deleteAll()
    }
}