package net.grandcentrix.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class ResponseData (
    val data: List<DataItem>
)

@Serializable
data class DataItem (
    val id: String,
    val attributes: MutableMap<String,String>
)