package com.example.oncash.Repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.oncash.Component.get_UserInfo_UseCase
import com.example.oncash.DataType.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class UserInfo_Airtable_Repo {
    private val apiKey = "keyCQq6gmGFzeqDCX"
    private val base = "appK86XkkYn9dx2vu"


    suspend fun getWallet(userRecordId: String): Int = withContext(Dispatchers.IO) {

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        val tableId = "tblOwifipGGANDJPN"
        val url = "https://api.airtable.com/v0/$base/$tableId/$userRecordId/"
        val response = client.get(url) {
            parameter(
                "api_key", apiKey
            )
        }

        val fields: JSONObject = JSONObject(response.body<String>()).getJSONObject("fields")
        val wallet = JSONObject(fields.toString()).getString("Wallet")

        return@withContext wallet.toInt()
    }

    suspend fun getUserInfo(): MutableLiveData<JSONArray> = withContext(Dispatchers.IO) {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val tableId = "tblOwifipGGANDJPN"
        val url = "https://api.airtable.com/v0/$base/$tableId/"
        val users: MutableLiveData<JSONArray> = MutableLiveData()
        try {
            val response = client.get(url) {
                parameter(
                    "api_key", apiKey
                )
            }


            users.postValue(JSONArray(JSONObject(response.body<String>()).getString("records")))
        } catch (e: Exception) {
            users.postValue(null)
        }
        return@withContext users

    }


    suspend fun getWithdrawTransaction(): MutableLiveData<JSONArray> = withContext(Dispatchers.IO) {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val tableId = "tblqaEsr4ImCVFvZb"
        val url = "https://api.airtable.com/v0/$base/$tableId/"
        val withdrawalTransaction: MutableLiveData<JSONArray> = MutableLiveData()
        try {
            val response = client.get(url) {
                parameter(
                    "api_key", apiKey
                )
            }


            withdrawalTransaction.postValue(JSONArray(JSONObject(response.body<String>()).getString("records")))
        } catch (e: Exception) {
            withdrawalTransaction.postValue(null)
        }
        return@withContext withdrawalTransaction

    }

    suspend fun createUser(number: Long, wallet: Int): String =
        withContext(Dispatchers.IO) {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
            val tableId = "tblOwifipGGANDJPN"
            val url = "https://api.airtable.com/v0/$base/$tableId/"
            val userInfo = Records(Fields(number, wallet))
            val userRecordId: MutableLiveData<String> = MutableLiveData()


            try {
                val response = client.post {
                    url(url)
                    header("Authorization", "Bearer $apiKey")
                    contentType(ContentType.Application.Json)
                    setBody(userInfo)
                }

                userRecordId.postValue(JSONObject(response.body<String>()).getString("id").toString()).toString()
                Log.i("userData" , JSONObject(response.body<String>()).getString("id").toString())

            } catch (e: Exception) {
                Log.i("airtable", e.toString())
            }

            return@withContext userRecordId.getValue().toString()

        }

    suspend fun updateWallet(number: Long, wallet: Int, userRecordId: String): String =
        withContext(Dispatchers.IO) {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
            val tableId = "tblOwifipGGANDJPN"
            val url = "https://api.airtable.com/v0/$base/$tableId/$userRecordId/"


            val userInfo = Records(Fields(number, wallet))
            var status by Delegates.notNull<String>()
            try {
                val response = client.patch {
                    url(url)
                    header("Authorization", "Bearer $apiKey")
                    contentType(ContentType.Application.Json)
                    setBody(userInfo)
                }
                status = response.status.toString()
                Log.i("airtabledata", response.body<String>().toString())

            } catch (e: Exception) {
                Log.i("airtable", e.toString())
            }

            return@withContext status

        }

    suspend fun withdrawRequest(
        phone: Long,
        RequestedAmount: Int,
        WalletBalance: Int,
        userRecordId: String
    ): withdrawalsuccess =
        withContext(Dispatchers.IO) {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
            val tableId = "tblqaEsr4ImCVFvZb"
            val url = "https://api.airtable.com/v0/$base/$tableId/"
            val userInfo = RecordsX(FieldsX(RequestedAmount, phone, WalletBalance , "Pending"))
            var date : String = ""
            var status = ""
            var walletstatus: Int = 0
            var responseStatus by Delegates.notNull<String>()
            try {
                val response = client.post {
                    url(url)
                    header("Authorization", "Bearer $apiKey")
                    contentType(ContentType.Application.Json)
                    setBody(userInfo)
                }
                responseStatus = response.status.toString()
                Log.i("userdataa" , response.body<String>().toString())
                val data = JSONObject(response.body<String>()).getString("createdTime")

                status = JSONObject(JSONObject(response.body<String>()).getString("fields")).getString("Status")
                date =  data.toString().split("T").get(0).toString()
                Log.i("dateee" , date.toString())
                Log.i("dateee" , status)
            } catch (e: Exception) {
                Log.i("withdrawData", e.toString())
            }

            return@withContext withdrawalsuccess(withdrawalTransaction(  date ,  RequestedAmount.toString()   , status ,) , responseStatus)
        }

}
