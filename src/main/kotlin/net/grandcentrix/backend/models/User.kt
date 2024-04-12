package net.grandcentrix.backend.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
    @Contextual
    val house: House,
    val favouriteItems: MutableList<String>
)