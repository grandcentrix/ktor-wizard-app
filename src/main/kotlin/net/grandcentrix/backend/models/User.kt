package net.grandcentrix.backend.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import java.util.*

@Serializable
data class User(
    val name: String,
    val surname: String,
    val email: String,
    val username: String,
    val password: String,
    val house: House? = null,
    val favouriteItems: MutableList<String> = mutableListOf(),
    val id: String = UUID.randomUUID().toString()
)

// define the table properties
object Users : Table() {
    val id = varchar("id", 128)
    val name = varchar("name", 128)
    val surname = varchar("surname", 128)
    val email = varchar("email", 128)
    val username = varchar("username", 128)
    val password = varchar("password", 64)
    val house = varchar("house", 128)
    val favouriteItems = varchar("favouriteItems", 128)
    override val primaryKey = PrimaryKey(id)
}