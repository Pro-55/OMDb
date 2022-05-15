package com.example.omdb.util.extensions

import android.util.Log
import com.example.omdb.models.Resource
import com.example.omdb.util.Constants
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.asIoFlow(doRetry: Boolean = false): Flow<T> {
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
            it.printStackTrace()
            FirebaseCrashlytics.getInstance().recordException(it)
            when (it) {
                is IllegalArgumentException -> Log.d("IF", "TestLog: IllegalArgumentException")
                else -> Log.d("IF", "TestLog: Exception")
            }
        }
        .flowOn(Dispatchers.IO)
}

fun <T> Flow<Resource<T>>.asResourceFlow(doRetry: Boolean = false): Flow<Resource<T>> {
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
            it.printStackTrace()
            FirebaseCrashlytics.getInstance().recordException(it)
            when (it) {
                is IllegalArgumentException -> {
                    Log.d("RF", "TestLog: IllegalArgumentException")
                    emit(Resource.error(msg = Constants.REQUEST_FAILED_MESSAGE, data = null))
                }
                else -> {
                    Log.d("RF", "TestLog: Exception")
                    emit(Resource.error(msg = Constants.REQUEST_FAILED_MESSAGE, data = null))
                }
            }
        }
        .flowOn(Dispatchers.IO)
}