package net.grandcentrix.backend.dao

import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.models.Users
import net.grandcentrix.backend.plugins.DAOException
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
            users[id] = user.id
            users[name] = user.name
            users[surname] = user.surname
            users[email] = user.email
            users[username] = user.username
            users[password] = user.password
            users[house] = user.house.toString()
            users[favouriteItems] = user.favouriteItems.joinToString(",")
            users[profilePictureData] = user.profilePictureData
        }
        if (insertStatement.resultedValues?.singleOrNull() == null) {
            throw DAOException("Failed to add user with username: ${user.username}")
        }
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
        val deleteCount = Users.deleteWhere { Users.username eq username }
        if (deleteCount == 0) {
            throw DAOException("Failed to delete user with username: $username")
        }
    }

    override fun updateItem(user: User) {
        transaction {
            val updateCount = Users.update({ Users.username eq user.username }) { users ->
                users[name] = user.name
                users[surname] = user.surname
                users[email] = user.email
                users[password] = user.password
                users[house] = user.house.toString()
                users[favouriteItems] = user.favouriteItems.joinToString(",")
                users[profilePictureData] = user.profilePictureData
            }
            if (updateCount == 0) {
                throw DAOException("Failed to update user with username: ${user.username}")
            }
        }
    }

    fun updateUsername(currentUsername: String, newUsername: String) {
        transaction {
            val updateCount = Users.update({ Users.username eq currentUsername }) {
                it[username] = newUsername
            }
            if (updateCount == 0) {
                throw DAOException("Failed to update username from $currentUsername to $newUsername")
            } else if (updateCount > 1) {
                throw DAOException("Updated more than one username unexpectedly")
            }
        }
    }



    fun updateProfilePicture(username: String, imageData: ByteArray) {
        transaction {
            val updateCount = Users.update({ Users.username eq username }) {
                it[profilePictureData] = imageData
            }
            if (updateCount == 0) {
                throw DAOException("Failed to update profile picture for user with username: $username")
            }
        }
    }

    fun removeProfilePicture(username: String) {
        transaction {
            Users.update({ Users.username eq username }) {
                it[profilePictureData] = null
            }

            // Check if profilePictureData is null after the update
            if (Users.select { Users.username eq username }
                    .singleOrNull()?.get(Users.profilePictureData) != null) {
                throw DAOException("Failed to remove profile picture for user with username: $username")
            }
        }
    }

    fun updatePassword(username: String, newPassword: String) {
        transaction {
            val updateCount = Users.update({ Users.username eq username }) {
                it[password] = newPassword
            }
            if (updateCount == 0) {
                throw DAOException("Failed to update password for user with username: $username")
            }
        }
    }

    fun updateEmail(username: String, newEmail: String) {
        transaction {
            val updateCount = Users.update({ Users.username eq username }) {
                it[email] = newEmail
            }
            if (updateCount == 0) {
                throw DAOException("Failed to update email for user with username: $username")
            }
        }
    }
}


val daoUsers = DAOUsers()