package com.example.omdb.util.wrappers

import com.example.omdb.models.Resource
import com.example.omdb.util.extensions.asIoFlow
import com.example.omdb.util.extensions.asResourceFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

fun <T> ioFlow(
    doRetry: Boolean = false,
    block: suspend FlowCollector<T>.() -> Unit
): Flow<T> = flow(block).asIoFlow(doRetry)

fun <T> resourceFlow(
    doRetry: Boolean = false,
    block: suspend FlowCollector<Resource<T>>.() -> Unit
): Flow<Resource<T>> {
    return flow(block).asResourceFlow(doRetry)
}