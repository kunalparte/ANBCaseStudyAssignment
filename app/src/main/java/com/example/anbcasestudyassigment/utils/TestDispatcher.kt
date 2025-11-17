package com.example.anbcasestudyassigment.utils

import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcher(private val dispatcher : CoroutineDispatcher):
    CoroutineDispatcherProvider {
    override val mainDispatcher: CoroutineDispatcher
        get() = dispatcher
    override val ioDispatcher: CoroutineDispatcher
        get() = dispatcher
    override val defaultDispatcher: CoroutineDispatcher
        get() = dispatcher
}