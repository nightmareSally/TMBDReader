package com.sally.tmbdreader.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8

class ApiErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.isSuccessful) {
            return response
        }

        val responseBody = response.body!!
        val source = responseBody.source()
        source.request(Long.MAX_VALUE)
        val buffer = source.buffer
        val contentType = responseBody.contentType()
        val charset: Charset = contentType?.charset(UTF_8) ?: UTF_8
        val responseString = buffer.clone().readString(charset)

        return try {
            val responseJson = JSONObject(responseString)
            if (responseJson.has("status_message") && responseJson.has("status_code")) {
                val code = responseJson.getInt("status_code")
                val message = responseJson.getString("status_message")
                throw TMBDError(code, message)
            }
            response
        } catch (e: TMBDError) {
            throw e
        } catch (e: Exception) {
            response
        }
    }
}