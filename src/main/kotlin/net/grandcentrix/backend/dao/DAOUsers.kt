package net.grandcentrix.backend.dao

import kotlinx.coroutines.runBlocking
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class DAOUsers : DAOFacade {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        name = row[Users.name],
        surname = row[Users.surname],
        email = row[Users.email],
        username = row[Users.username],
        password = row[Users.password],
        house = row[Users.house],
        favouriteItems = row[Users.favouriteItems].split(",").toMutableList(),
        profilePictureData = row[Users.profilePictureData]
    )

    override fun getAll(): List<User> = transaction {
        Users.selectAll().map(::resultRowToUser)
    }

    override fun addItem(user: User): Unit = transaction {
        val insertStatement = Users.insertIgnore { users ->
            users[name] = user.name
            users[surname] = user.surname
            users[email] = user.email
            users[username] = user.username
            users[password] = user.password
            users[house] = user.house.toString()
            users[favouriteItems] = user.favouriteItems.joinToString(",")
            users[profilePictureData] = user.profilePictureData
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override fun getItem(username: String): User? = transaction {
        Users
            .select { Users.username eq username }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    fun getByEmail(email: String): User? = transaction {
        Users
            .select { Users.email eq email }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    fun getProfilePictureData(username: String): ByteArray? {
        return transaction {
            Users.select { Users.username eq username }
                .map { it[Users.profilePictureData] }
                .singleOrNull()
        }
    }

    fun getHouse(username: String): String? {
        return transaction {
            Users.select { Users.username eq username }
                .map { it[Users.house] }
                .singleOrNull()
        }
    }


    override fun deleteItem(username: String): Unit = transaction {
        Users.deleteWhere { Users.username eq username } > 0
    }

    override fun updateItem(user: User) {
        Users.update({ Users.username eq user.username }) { users ->
            users[name] = user.name
            users[surname] = user.surname
            users[email] = user.email
            users[password] = user.password
            users[house] = user.house.toString()
            users[favouriteItems] = user.favouriteItems.joinToString(",")
            users[profilePictureData] = user.profilePictureData
        } > 0
    }

    fun updateUsername(currentUsername: String, newUsername: String): Boolean = transaction {

            Users.update({ Users.username eq currentUsername }) {
                it[username] = newUsername
            } > 0
        }


    fun updateProfilePicture(username: String, imageData: ByteArray):  Boolean = transaction {

            Users.update({ Users.username eq username }) {
                it[profilePictureData] = imageData
            } > 0
        }


    fun removeProfilePicture(username: String): Boolean = transaction {
        Users.update({ Users.username eq username }) {
            it[profilePictureData] = null
        }

        // Check if profilePictureData is null after the update
        Users.select { Users.username eq username }
            .singleOrNull()?.get(Users.profilePictureData) == null
    }

    fun updatePassword(username: String, newPassword: Int): Boolean = transaction {
        Users.update({ Users.username eq username }) {
            it[password] = newPassword
        } > 0
    }


    fun updateEmail(username: String, newEmail: String):  Boolean = transaction {
            Users.update({ Users.username eq username }) {
                it[email] = newEmail
            } > 0
        }
    }


val daoUsers = DAOUsers().apply {

}
