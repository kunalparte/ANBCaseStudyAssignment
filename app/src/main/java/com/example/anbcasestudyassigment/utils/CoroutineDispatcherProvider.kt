package com.example.anbcasestudyassigment.utils

import kotlinx.coroutines.CoroutineDispatcher

sealed interface CoroutineDispatcherProvider {
    val mainDispatcher : CoroutineDispatcher
    val ioDispatcher : CoroutineDispatcher
    val defaultDispatcher : CoroutineDispatcher
}