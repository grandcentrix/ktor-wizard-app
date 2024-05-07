package net.grandcentrix.backend.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val username: String,
    val password: Int,
    @Contextual
    val house: House? = null,
    val favouriteItems: MutableList<String> = mutableListOf(),
    val profilePictureData: ByteArray? = null
)

// define the table properties
object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val surname = varchar("surname", 128)
    val email = varchar("email", 128)
    val username = varchar("username", 128)
    val password = integer("password")
    val house = varchar("house", 128)
    val favouriteItems = varchar("favouriteItems", 128)
    val profilePictureData = binary("profilePictureData").nullable()
    override val primaryKey = PrimaryKey(id)
}
