package com.example.omdb.data.network.model

import com.example.omdb.util.Constants

sealed interface Response<T> {
    class Success<T>(val data: T?) : Response<T>

    class UnknownHostException<T> : Response<T>

    class InvalidPathException<T>(
        val msg: String = Constants.ERROR_MESSAGE_INVALID_PATH
    ) : Response<T>

    class InvalidRequestException<T>(
        val msg: String = Constants.ERROR_MESSAGE_INVALID_REQUEST
    ) : Response<T>

    class ServerException<T>(
        val msg: String = Constants.ERROR_MESSAGE_SERVER_EXCEPTION
    ) : Response<T>

    class RequestTimeoutException<T>(
        val msg: String = Constants.ERROR_MESSAGE_REQUEST_TIMEOUT_EXCEPTION
    ) : Response<T>

    class UnknownException<T>(
        val msg: String = Constants.ERROR_MESSAGE_UNKNOWN
    ) : Response<T>
}