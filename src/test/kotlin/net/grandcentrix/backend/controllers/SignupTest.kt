package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.plugins.*
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.InvalidValue
import net.grandcentrix.backend.plugins.UserAlreadyExistsException
import org.junit.Test
import kotlin.test.*

class SignupTest {

    companion object {
        val users = mutableListOf<User>()
    }

    init {
        // connect to the application database
        val config = ApplicationConfig("./src/main/resources/application.conf")
        DatabaseSingleton.init(config)
        // retrieve a small population of users
//        users.addAll(daoUsers.getAll().subList(0, 3))
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
    fun testCreateUserWithMissingFields() {
        val formParameters = Parameters.build {
            append("name", "")
            append("surname", "")
            append("email", "personone@email.com")
            append("username", "")
            append("password", "123")
            append("house", "")
        }

        assertFailsWith(
            exceptionClass = MissingRequestParameterException::class,
            message = "Missing required fields!"
        )   {
                SignupInstance.createUser(formParameters)
            }
    }

    @Test
    fun testCreateUserWitDuplicatedUsername() {
        val duplicatedUsername = daoUsers.getAll().first().username
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "One")
            append("email", "personone@email.com")
            append("username", duplicatedUsername)
            append("password", "123")
            append("house", "")
        }

        assertFailsWith(
            exceptionClass = UserAlreadyExistsException::class,
            message = "Username is already in use!"
        )   {
                SignupInstance.createUser(formParameters)
            }
    }

    @Test
    fun testCreateUserWitDuplicatedEmail() {
        val duplicatedEmail = daoUsers.getAll().first().email
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "One")
            append("email", duplicatedEmail)
            append("username", "personone")
            append("password", "123")
            append("house", "")
        }

        assertFailsWith(
            exceptionClass = UserAlreadyExistsException::class,
            message = "Email is already in use!"
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testCreateUserWithoutHouse() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "One")
            append("email", "personone@email.com")
            append("username", "personone")
            append("password", "123")
            append("house", "")
        }

        SignupInstance.createUser(formParameters)
        val user = daoUsers.getItem(formParameters["username"]!!)

        assertNull(user!!.house)

        // delete the created user
        daoUsers.deleteItem("personone")
    }

    @Test
    fun testCreateUserWithHouse() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Two")
            append("email", "persontwo@email.com")
            append("username", "persontwo")
            append("password", "123")
            append("houses", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        SignupInstance.createUser(formParameters)
        val user = daoUsers.getItem(formParameters["username"].toString())

        assertNotNull(user!!.house)
        assertIs<House>(user.house)
        assertEquals(
            expected = formParameters["houses"]!!,
            actual = user.house!!.id
        )

        // delete the created user
        daoUsers.deleteItem("persontwo")
    }

    @Test
    fun testInvalidEmailAddress() {
        // delete the created user
        daoUsers.deleteItem("personone")

        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "One")
            append("email", "personone@email")
            append("username", "personone")
            append("password", "123")
            append("houses", "")
        }

        assertFailsWith(
            exceptionClass = InvalidValue::class,
            message = "Invalid value for e-mail!"
        )   {
            SignupInstance.createUser(formParameters)
        }

        // delete the created user
        daoUsers.deleteItem("personone")
    }

    @Test
    fun testInvalidUsername() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "One")
            append("email", "personone@email")
            append("username", "!personone!")
            append("password", "123")
            append("houses", "")
        }

        assertFailsWith(
            exceptionClass = InvalidValue::class,
            message = "Invalid value for username!"
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testInvalidName() {
        val formParameters = Parameters.build {
            append("name", "1234")
            append("surname", "Person")
            append("email", "personone@email")
            append("username", "personone")
            append("password", "123")
            append("houses", "")
        }

        assertFailsWith(
            exceptionClass = InvalidValue::class,
            message = "Invalid value for name!"
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

}