package com.example.oncash.DataType
import kotlinx.serialization.Serializable

@Serializable
data class Fields(
    val UserPhone: Long,
    val Wallet: Int
)