package net.grandcentrix.backend.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData<T> (
    val data: List<DataItem<T>>
)

@Serializable
data class ResponseObject<T>(
    val data: DataItem<T>
)

@Serializable
data class DataItem<T> (
    val id: String,
    val attributes: T
)

@Serializable
data class GravatarProfile (
    val hash: String = String(),
    @SerialName("display_name")
    val displayName: String = String(),
    @SerialName("profile_url")
    val url: String = String(),
    @SerialName("avatar_url")
    val avatarUrl: String = String(),
    val error: String = String(),
    val code: String = String()
)