package net.grandcentrix.backend.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData<T> (
    val data: List<DataItem<T>>
)

@Serializable
data class DataItem<T>(
    val attributes: T
)

@Serializable
data class GravatarProfile (
    val hash: String,
    @SerialName("display_name")
    val displayName: String,
    @SerialName("profile_url")
    val url: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)