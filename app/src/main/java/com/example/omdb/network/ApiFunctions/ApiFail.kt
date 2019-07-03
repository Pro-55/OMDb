package com.example.omdb.network.ApiFunctions

import java.io.IOException

import retrofit2.adapter.rxjava.HttpException
import rx.functions.Action1

abstract class ApiFail : Action1<Throwable> {
    override fun call(throwable: Throwable) {
        if (throwable is HttpException) {
            val response = throwable.response()?.let { HttpErrorResponse(throwable.code(), it) }
            if (response != null) {
                response.error = throwable.message()
            }
            if (response != null) {
                httpStatus(response)
            }
        } else if (throwable is IOException) {
            noNetworkError()
        } else {
            unknownError(throwable)
        }
    }

    abstract fun httpStatus(response: HttpErrorResponse)

    abstract fun noNetworkError()

    abstract fun unknownError(throwable: Throwable)
}
