package com.example.omdb.util.extensions

import android.util.Log
import com.example.omdb.models.Resource
import com.example.omdb.util.Constants
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

fun <T> ioFlow(doRetry: Boolean = true, block: suspend FlowCollector<T>.() -> Unit): Flow<T> {
    return flow(block).asIoFlow(doRetry)
}

fun <T> Flow<T>.asIoFlow(doRetry: Boolean = true): Flow<T> {
    return this
        .distinctUntilChanged()
        .retryWhen { cause, attempt ->
            // Exponential backoff of 1 second on each retry
            if (attempt > 1) delay(1000 * attempt)

            // Do not retry for IllegalArgument or 3 attempts are reached
            if (cause is IllegalArgumentException || attempt == 3L) false
            else doRetry
        }
        .catch {
            when (it) {
                is IllegalArgumentException -> {
                    Log.d("ioFlow", "TestLog: Couldn't complete request: IllegalArgumentException")
                }
                else -> {
                    it.printStackTrace()
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
            }
        }
        .flowOn(Dispatchers.IO)
}

fun <T> resourceFlow(
    doRetry: Boolean = true,
    block: suspend FlowCollector<Resource<T>>.() -> Unit
): Flow<Resource<T>> {
    return flow(block).asResourceFlow(doRetry)
}

fun <T> Flow<Resource<T>>.asResourceFlow(doRetry: Boolean = true): Flow<Resource<T>> {
    return this
        .onStart { emit(Resource.loading(data = null)) }
        .distinctUntilChanged()
        .retryWhen { cause, attempt ->
            // Exponential backoff of 1 second on each retry
            if (attempt > 1) delay(1000 * attempt)

            // Do not retry for IllegalArgument or 3 attempts are reached
            if (cause is IllegalArgumentException || attempt == 3L) false
            else doRetry
        }
        .catch {
            when (it) {
                is IllegalArgumentException -> {
                    Log.d(
                        "resourceFlow",
                        "TestLog: Couldn't complete request: IllegalArgumentException"
                    )
                    emit(Resource.error(msg = Constants.REQUEST_FAILED_MESSAGE, data = null))
                }
                else -> {
                    it.printStackTrace()
                    FirebaseCrashlytics.getInstance().recordException(it)
                    emit(Resource.error(msg = Constants.REQUEST_FAILED_MESSAGE, data = null))
                }
            }
        }
        .flowOn(Dispatchers.IO)
}