package net.grandcentrix.backend

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.grandcentrix.backend.models.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.DriverManager

object TestDatabaseSetup {

    private const val DRIVER_CLASS_NAME = "org.sqlite.JDBC"
    private const val JDBC_URL = "jdbc:sqlite::memory:"
    private const val MAX_POOL_SIZE = 6

    fun startDatabase() {

        DriverManager.getConnection(JDBC_URL).use {
            println("SQLite database created successfully!")
        }

        // Create a custom config for the database with pool size
        val config: HikariConfig = HikariConfig().apply {
            jdbcUrl = JDBC_URL
            driverClassName = DRIVER_CLASS_NAME
            maximumPoolSize = MAX_POOL_SIZE
        }

        // Connects with the database
        val database = Database.connect(HikariDataSource(config))

        transaction(database) {
            // creates the tables
            SchemaUtils.create(Users)

        }

        println("Database initialized successfully!")
    }

}