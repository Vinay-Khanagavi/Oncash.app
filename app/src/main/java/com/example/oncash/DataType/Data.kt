package com.example.oncash.DataType

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("UserId")
    val UserId: String ,
    @SerializedName("UserPhone")
    val UserNumber :Int ,
    @SerializedName("Wallet")
    val Wallet : Int
)