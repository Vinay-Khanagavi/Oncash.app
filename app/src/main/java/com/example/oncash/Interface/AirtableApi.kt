package com.example.oncash.Interface

import com.example.oncash.DataType.AirtableData
import com.example.oncash.DataType.Data
import retrofit2.Call
import retrofit2.http.GET

interface AirtableApi {
    @GET("/")
    fun getRecords(): Call<String>
}