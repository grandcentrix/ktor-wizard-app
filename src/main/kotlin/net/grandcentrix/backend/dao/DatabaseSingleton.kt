package net.grandcentrix.backend.dao

import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.DriverManager

object DatabaseSingleton {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("storage.driverClassName").getString()
        val url = config.property("storage.jdbcURL").getString()

        // Open a connection to the database (creates if not exists)
        DriverManager.getConnection(url).use { connection ->
            println("SQLite database created successfully")
        }

        // Connects with the database
        val database = Database.connect(url, driverClassName)

        transaction(database) {
            // creates the tables
//            SchemaUtils.create(Users)
        }
    }
}
