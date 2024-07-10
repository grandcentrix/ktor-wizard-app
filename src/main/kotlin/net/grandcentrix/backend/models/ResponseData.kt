package net.grandcentrix.backend.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData<T> (
    val data: List<DataItem<T>>,
    val pagination: Pagination? = null,
    val paginationLinks: PaginationLinks? = null
)

@Serializable
data class DataItem<T> (
    val id: String,
    val attributes: T
)

@Serializable
data class Pagination (
    val current: Int,
    val first: Int?,
    val previous: Int?,
    val next: Int?,
    val last: Int?,
    val records: Int
)

@Serializable
data class PaginationLinks (
    val self: String,
    val current: String,
    val first: String?,
    val previous: String?,
    val next: String?,
    val last: String?
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