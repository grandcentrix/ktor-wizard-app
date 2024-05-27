package net.grandcentrix.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class ResponseData<T> (
    val data: List<DataItem<T>>
)

@Serializable
data class DataItem<T>(
    val attributes: T
)