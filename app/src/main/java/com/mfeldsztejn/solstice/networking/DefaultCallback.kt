package com.mfeldsztejn.solstice.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultCallback<T>: Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        RestApiBuilder.post(ApiError(500, t.message!!))
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            RestApiBuilder.post(response.body() as Any)
        } else {
            RestApiBuilder.post(ApiError(response.code(), response.message()))
        }
    }
}

data class ApiError(val statusCode: Int, val message: String);