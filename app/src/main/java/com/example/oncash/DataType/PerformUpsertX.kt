package com.example.oncash.DataType
@kotlinx.serialization.Serializable
data class PerformUpsertX(
    val fieldsToMergeOn: List<String>
)