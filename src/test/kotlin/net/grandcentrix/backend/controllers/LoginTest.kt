package net.grandcentrix.backend.controllers

import io.ktor.server.auth.*
import io.ktor.server.config.*
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import kotlin.test.*

class LoginTest {
    companion object {
        val users = mutableListOf<User>()
    }

    init {
        // connect to the application database
        val config = ApplicationConfig("./src/main/resources/application.conf")
        DatabaseSingleton.init(config)
        // retrieve a small population of users
        users.addAll(daoUsers.getAll().subList(0, 3))
    }

    @BeforeTest
    fun beforeTest() {
        // set a test database using a configuration file for tests
        val configTest = ApplicationConfig("./src/main/resources/test_application.conf")
        DatabaseSingleton.init(configTest)
        // add the small population from the app database to the test database
        for (user in users) {
            daoUsers.addItem(user)
        }
    }

    @AfterTest
    fun afterTest() {
        for (user in users) {
            daoUsers.deleteItem(user.username)
        }
    }

    @Test
    fun testVerifyLoginSuccess() {
        // get existing user and password
        val username = daoUsers.getAll().first().username
        val password = daoUsers.getAll().first().password.toString() //FIXME hash password
        // create credentials
        val credentials = UserPasswordCredential(username, password)

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