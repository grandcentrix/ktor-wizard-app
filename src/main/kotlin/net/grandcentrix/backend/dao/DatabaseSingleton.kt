package net.grandcentrix.backend.dao

import io.ktor.server.config.*
import net.grandcentrix.backend.models.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.sql.DriverManager

object DatabaseSingleton {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("storage.driverClassName").getString()
        val url = config.property("storage.jdbcURL").getString()


        // Check if a database exists
        val databaseFile = File(url.substringAfter("jdbc:sqlite:"))
        if (!databaseFile.exists()) {
            createDatabase(url)
        }

        // Connects with the database
        val database = Database.connect(url, driverClassName)

        transaction(database) {
            // Create or update the Users table schema
            SchemaUtils.createMissingTablesAndColumns(
                Users,
                FavouriteBooks,
                FavouriteCharacters,
                FavouriteHouses,
                FavouriteMovies,
                FavouritePotions,
                FavouriteSpells,
                Characters
            )

            }

        println("Database initialized successfully!")
    }

    private fun createDatabase(url: String) {
        DriverManager.getConnection(url).use { connection ->
            println("SQLite database created successfully!")
        }
    }
}



