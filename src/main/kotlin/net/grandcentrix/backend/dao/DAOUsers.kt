package net.grandcentrix.backend.dao

import kotlinx.coroutines.runBlocking
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.models.Users
import net.grandcentrix.backend.repository.HousesRepository.Companion.HousesRepositoryInstance
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class DAOUsers: DAOFacade {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        name = row[Users.name],
        surname = row[Users.surname],
        email = row[Users.email],
        username = row[Users.username],
        password = row[Users.password],
        house = HousesRepositoryInstance.getItem(row[Users.house]),
        favouriteItems = row[Users.favouriteItems].split(",").toMutableList()
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
            users[house] = user.house?.name.toString()
            users[favouriteItems] = user.favouriteItems.toString()
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

    override fun deleteItem(username: String): Unit = transaction {
        Users.deleteWhere { Users.username eq username } > 0
    }

    override fun updateItem(user: User) {
        Users.update({ Users.username eq user.username }) { users ->
            users[name] = user.name
            users[surname] = user.surname
            users[email] = user.email
            users[password] = user.password
            users[house] = user.house?.name.toString()
            users[favouriteItems] = user.favouriteItems.toString()
        } > 0
    }

}

val daoUsers = DAOUsers().apply {
    runBlocking {

    }
}