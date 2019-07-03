package com.example.omdb.network.ApiFunctions

import retrofit2.Response

class HttpErrorResponse(val httpStatusCode: Int, val response: Response<*>) {

    var error: String? = null

    object HTTP_STATUS {
        val STATUS_200 = 200
        val STATUS_420 = 420
        val STATUS_404 = 404
        val STATUS_400 = 400
        val STATUS_403 = 403

    }
}
