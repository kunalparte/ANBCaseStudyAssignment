package com.example.anbcasestudyassigment.core.network

import java.lang.reflect.Type

interface ApiClient {

    suspend fun<T> getDataFromApiCall(
        endPointUrl: String,
        queryParamMap: HashMap<String, Any>,
        responseType : Type,
        onSuccess: (T) -> Unit,
        onError: (error: String) -> Unit
    )
}