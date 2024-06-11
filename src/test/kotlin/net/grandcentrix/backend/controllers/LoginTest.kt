package net.grandcentrix.backend.controllers

import io.ktor.server.auth.*
import io.ktor.server.config.*
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import kotlin.test.*

class LoginTest {

    @BeforeTest
    fun beforeTest() {
        val user1 = User(
            1,
            "Person",
            "One",
            "personone@email.com",
            "personone",
            123,
            null
        )

        val user2 = User(
            2,
            "Person",
            "Two",
            "persontwo@email.com",
            "persontwo",
            123,
            null
        )

        val user3 = User(
            3,
            "Person",
            "Three",
            "personthree@email.com",
            "personthree",
            123,
            null
        )

        // set a test database using a configuration file for tests
        val configTest = ApplicationConfig("./src/main/resources/test_application.conf")
        DatabaseSingleton.init(configTest)
        // add the small population from the app database to the test database
        daoUsers.addItem(user1)
        daoUsers.addItem(user2)
        daoUsers.addItem(user3)
    }

    @AfterTest
    fun afterTest() {
        for (user in daoUsers.getAll()) {
            daoUsers.deleteItem(user.username)
        }
    }

    @Test
    fun testVerifyLoginSuccess() {
        val username = daoUsers.getAll().first().username
        val password = daoUsers.getAll().first().password.toString()
        val credentials = UserPasswordCredential( username, password)
        assertTrue(LoginInstance.verifyLogin(credentials))
    }

    @Test
    fun testVerifyLoginFails() {
        // non-matching credentials
        val username = daoUsers.getAll().first().username
        val password = daoUsers.getAll().last().password.toString()
        val credentials = UserPasswordCredential(username, password)

        assertFalse(LoginInstance.verifyLogin(credentials))
    }
}