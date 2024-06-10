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
    val house: String?,
    @Contextual
    val favouriteItems: MutableList<String> = mutableListOf(),
    val profilePictureData: ByteArray? = null //to store profile picture data as a ByteArray. This allows storing the image data directly in the database.
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (profilePictureData != null) {
            if (other.profilePictureData == null) return false
            if (!profilePictureData.contentEquals(other.profilePictureData)) return false
        } else if (other.profilePictureData != null) return false

        return true
    }

    override fun hashCode(): Int {
        return profilePictureData?.contentHashCode() ?: 0
    }
}

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
    val profilePictureData = binary("profilePictureData").nullable() // Allows storing raw binary data for the profile picture, and permits NULL values if no image is provided.
    override val primaryKey = PrimaryKey(id)
}
