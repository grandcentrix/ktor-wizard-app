package net.grandcentrix.backend.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import net.grandcentrix.backend.models.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.sql.DriverManager

object DatabaseSingleton {
    fun init(config: ApplicationConfig) {
        val driver = config.property("storage.driverClassName").getString()
        val url = config.property("storage.jdbcURL").getString()
        val poolSize = config.property("storage.maxPoolSize").getString().toInt()

        val hikariConfig: HikariConfig = HikariConfig().apply {
            jdbcUrl = url
            driverClassName = driver
            maximumPoolSize = poolSize
        }

        // Check if a database exists
        val databaseFile = File(url.substringAfter("jdbc:sqlite:"))
        if (!databaseFile.exists()) {
            createDatabase(url)
        }

        // Connects with the database
        val database = Database.connect(HikariDataSource(hikariConfig))


        transaction(database) {
            // Create or update the Users table schema
            SchemaUtils.createMissingTablesAndColumns(Users)
        }

        println("Database initialized successfully!")

    }

    private fun createDatabase(url: String) {
        DriverManager.getConnection(url).use { connection ->
            println("SQLite database created successfully!")
        }
    }
}
