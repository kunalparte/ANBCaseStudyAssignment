package com.example.anbcasestudyassigment.core.network

import android.util.Log
import com.google.gson.Gson
import java.lang.Exception
import java.lang.reflect.Type
import javax.inject.Inject

class ApiClientImpl @Inject constructor(
    private val apiService: ApiService
): ApiClient {
    override suspend fun <T> getDataFromApiCall(
        endPointUrl: String,
        queryParamMap: HashMap<String, Any>,
        responseType: Type,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit
    ) {
        when(endPointUrl){
            ApiConstants.SEARCH_BOOKS ->{
                getBookList( queryParamMap,responseType, onSuccess, onError)
            }
        }
    }

    private suspend fun <T> getBookList(
        queryParamMap: Map<String, Any>,
        responseType: Type,
        onSuccess: (data : T) -> Unit,
        onError: (String) -> Unit
    ) {
        try{
            val response = apiService.getBookList(page = queryParamMap.get("page") as Int)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    // Convert raw JSON to correct type
                    val json = Gson().toJson(body)
                    val result: T = Gson().fromJson(json, responseType)
                    onSuccess((result))
                } else {
                    onError("Response body is null")
                }
            } else {
                onError("Error ${response.code()}: ${response.message()}")
            }
        }catch (e: Exception){
            Log.e("Exception", e.message ?: "")
        }
    }
}