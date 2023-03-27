package com.example.oncash.Repository

import android.util.Log
import com.example.oncash.DataType.AirtableData
import com.example.oncash.DataType.Data
import com.example.oncash.Interface.AirtableApi

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class offer_AirtableDatabase {
    fun getData()
    {
        // Define the base URL for the Airtable API
        val userRecordId :String = "recW4kvQ98bYzYNpJ/"
        val baseUrl = "https://api.airtable.com/v0/appK86XkkYn9dx2vu/Table%201/$userRecordId"
        val token = "keyCQq6gmGFzeqDCX"
    }
}

