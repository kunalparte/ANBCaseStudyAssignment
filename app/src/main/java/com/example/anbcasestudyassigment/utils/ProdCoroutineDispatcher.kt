package com.example.anbcasestudyassigment.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ProdCoroutineDispatcher : CoroutineDispatcherProvider {
    override val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main

    override val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    override val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default
}