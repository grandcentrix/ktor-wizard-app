package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.dao.daoUsers
import kotlin.test.*

class LoginTest {

    @BeforeTest
    fun beforeTest() {
        val user1Parameters = Parameters.build {
            append("name", "Person")
            append("surname", "One")
            append("email", "personone@email.com")
            append("username", "personone")
            append("password", "123")
            append("house", "")
        }

        val user2Parameters = Parameters.build {
            append("name", "Person")
            append("surname", "Two")
            append("email", "persontwo@email.com")
            append("username", "persontwo")
            append("password", "123")
            append("house", "")
        }

        val user3Parameters = Parameters.build {
            append("name", "Person")
            append("surname", "Three")
            append("email", "personthree@email.com")
            append("username", "personthree")
            append("password", "123")
            append("house", "")
        }

        // set a test database using a configuration file for tests
        val configTest = ApplicationConfig("./src/main/resources/test_application.conf")
        DatabaseSingleton.init(configTest)
        // add the small population from the app database to the test database
        SignupInstance.createUser(user1Parameters)
        SignupInstance.createUser(user2Parameters)
        SignupInstance.createUser(user3Parameters)
    }

    @AfterTest
    fun afterTest() {
        for (user in daoUsers.getAll()) {
            daoUsers.deleteItem(user.username)
        }
    }

    @Test
    fun testVerifyLoginSuccess() {
        val credentials = UserPasswordCredential("personone", "123")
        assertTrue(LoginInstance.verifyLogin(credentials))
    }

    @Test
    fun testVerifyLoginFails() {
        // non-matching credentials
        val username = daoUsers.getAll().first().username
        val password = daoUsers.getAll().last().password
        val credentials = UserPasswordCredential(username, password)

        assertFalse(LoginInstance.verifyLogin(credentials))
    }
}