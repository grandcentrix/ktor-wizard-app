package net.grandcentrix.backend.dao

import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.models.Users
import net.grandcentrix.backend.plugins.DAOUsersException
import net.grandcentrix.backend.plugins.SignupException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


class DAOUsers {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        name = row[Users.name],
        surname = row[Users.surname],
        email = row[Users.email],
        username = row[Users.username],
        password = row[Users.password],
        house = row[Users.house],
        profilePictureData = row[Users.profilePictureData]
    )

    fun getAll(): List<User> = transaction {
        Users.selectAll().map(::resultRowToUser)
    }

    fun addItem(user: User): Unit = transaction {
        val insertStatement = Users.insertIgnore { users ->
            users[id] = user.id
            users[name] = user.name
            users[surname] = user.surname
            users[email] = user.email
            users[username] = user.username
            users[password] = user.password
            users[house] = user.house.toString()
            users[profilePictureData] = user.profilePictureData
        }
        if (insertStatement.insertedCount == 0) {
            throw SignupException("Failed to create account. E-mail and/or username must be taken.")
        }
     }

    fun getItem(username: String): User? = transaction {
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

    fun deleteItem(username: String): Unit = transaction {
        val deleteCount = Users.deleteWhere { Users.username eq username }
        if (deleteCount == 0) {
            throw DAOUsersException("Failed to delete user with username: $username")
        }
    }

    fun updateUsername(currentUsername: String, newUsername: String) {
        transaction {
            val updateCount = Users.update({ Users.username eq currentUsername }) {
                it[username] = newUsername
            }
            if (updateCount == 0) {
                throw DAOUsersException("Failed to update username from $currentUsername to $newUsername")
            } else if (updateCount > 1) {
                throw DAOUsersException("Updated more than one username unexpectedly")
            }
        }
    }

    fun updateProfilePicture(username: String, imageData: ByteArray) {
        transaction {
            val updateCount = Users.update({ Users.username eq username }) {
                it[profilePictureData] = imageData
            }
            if (updateCount == 0) {
                throw DAOUsersException("Failed to update profile picture for user with username: $username")
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
                throw DAOUsersException("Failed to remove profile picture for user with username: $username")
            }
        }
    }

    fun updatePassword(username: String, newPassword: String) {
        transaction {
            val updateCount = Users.update({ Users.username eq username }) {
                it[password] = newPassword
            }
            if (updateCount == 0) {
                throw DAOUsersException("Failed to update password for user with username: $username")
            }
        }
    }

    fun updateEmail(username: String, newEmail: String) {
        transaction {
            val updateCount = Users.update({ Users.username eq username }) {
                it[email] = newEmail
            }
            if (updateCount == 0) {
                throw DAOUsersException("Failed to update email for user with username: $username")
            }
        }
    }
}


val daoUsers = DAOUsers()