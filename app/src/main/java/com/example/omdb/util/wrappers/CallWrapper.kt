package com.example.omdb.util.wrappers

import com.example.analytics.Analytics
import com.example.omdb.data.network.model.Response
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import java.net.UnknownHostException

suspend fun <T> safeCall(block: suspend () -> Response<T>): Response<T> {
    return try {
        block.invoke()
    } catch (e: UnknownHostException) {
        Analytics.logException(e = e)
        Response.UnknownHostException()
    } catch (e: RedirectResponseException) {
        Analytics.logException(e = e)
        Response.InvalidPathException()
    } catch (e: ClientRequestException) {
        Analytics.logException(e = e)
        Response.InvalidRequestException()
    } catch (e: ServerResponseException) {
        Analytics.logException(e = e)
        Response.ServerException()
    } catch (e: HttpRequestTimeoutException) {
        Analytics.logException(e = e)
        Response.RequestTimeoutException()
    } catch (e: Exception) {
        Analytics.logException(e = e)
        Response.UnknownException()
    }
}