package com.example.omdb.util.wrappers

import com.example.omdb.models.network.Response
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import java.net.UnknownHostException

suspend fun <T> safeCall(block: suspend () -> Response<T>): Response<T> {
    return try {
        block.invoke()
    } catch (e: UnknownHostException) {
        e.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(e)
        Response.UnknownHostException()
    } catch (e: RedirectResponseException) {
        e.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(e)
        Response.InvalidPathException()
    } catch (e: ClientRequestException) {
        e.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(e)
        Response.InvalidRequestException()
    } catch (e: ServerResponseException) {
        e.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(e)
        Response.ServerException()
    } catch (e: Exception) {
        e.printStackTrace()
        FirebaseCrashlytics.getInstance().recordException(e)
        Response.UnknownException()
    }
}