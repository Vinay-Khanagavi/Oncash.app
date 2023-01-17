package com.example.oncash.DataType
import kotlinx.serialization.Serializable

@Serializable
data class FieldsX(
    val RequestedAmount: Int,
    val UserNumber: Long,
    val WalletBalance: Int,
    val Status: String
)