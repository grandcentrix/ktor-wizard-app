package net.grandcentrix.backend.dao

import kotlinx.coroutines.runBlocking
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.models.Users
import net.grandcentrix.backend.repository.HouseRepository.Companion.HouseRepositoryInstance
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DAOUsers: DAOFacade {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        name = row[Users.name],
        surname = row[Users.surname],
        email = row[Users.email],
        username = row[Users.username],
        password = row[Users.password],
        house = HouseRepositoryInstance.getItem(row[Users.house]),
        favouriteItems = row[Users.favouriteItems].split(",").toMutableList()
    )

    override fun getAll(): List<User> = transaction {
        Users.selectAll().map(::resultRowToUser)
    }

    override fun deleteItem(name: String) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: User) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: User): Unit = transaction {
        val insertStatement = Users.insert { users ->
            users[name] = item.name
            users[surname] = item.surname
            users[email] = item.email
            users[username] = item.username
            users[password] = item.password
            users[house] = item.house?.name.toString()
            users[favouriteItems] = item.favouriteItems.toString()
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

}

val daoUsers = DAOUsers().apply {
    runBlocking {

    }
}