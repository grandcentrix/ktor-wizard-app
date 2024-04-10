package net.grandcentrix.backend.models

import java.util.UUID

class User(
    val id: UUID,
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
    val house: House,
    val favouriteItems: MutableList<Any>
)