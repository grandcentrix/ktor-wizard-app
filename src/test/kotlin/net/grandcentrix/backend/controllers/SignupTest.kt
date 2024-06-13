package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.config.*
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.RequestException
import net.grandcentrix.backend.plugins.UserAlreadyExistsException
import org.junit.Test
import kotlin.test.*

class SignupTest {

    @BeforeTest
    fun beforeTest() {
      // create a population of users for test
        val user1 = User(
            "Person",
            "One",
            "personone@email.com",
            "personone",
            "123",
            null
        )

        val user2 = User(
            "Person",
            "Two",
            "persontwo@email.com",
            "persontwo",
            "123",
            null
        )

        val user3 = User(
            "Person",
            "Three",
            "personthree@email.com",
            "personthree",
            "123",
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
    fun testCreateUserWithMissingFields() {
        val formParameters = Parameters.build {
            append("name", "")
            append("surname", "")
            append("email", "personfour@email.com")
            append("username", "")
            append("password", "123")
            append("house", "")
        }

        assertFailsWith(
            exceptionClass = RequestException::class,
            message = "Missing required fields!"
        )   {
                SignupInstance.createUser(formParameters)
            }
    }

    @Test
    fun testCreateUserWitDuplicatedUsername() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Four")
            append("email", "personfour@email.com")
            append("username", "personone")
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
            append("surname", "Four")
            append("email", "personone@email.com")
            append("username", "personfour")
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
            append("surname", "Four")
            append("email", "personfour@email.com")
            append("username", "personfour")
            append("password", "123")
            append("house", "")
        }

        SignupInstance.createUser(formParameters)
        val user = daoUsers.getItem(formParameters["username"]!!)

        assertNull(user!!.house)

        // delete the created user
        daoUsers.deleteItem("personfour")
    }

    @Test
    fun testCreateUserWithHouse() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Four")
            append("email", "personfour@email.com")
            append("username", "personfour")
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
        daoUsers.deleteItem("personfour")
    }

    @Test
    fun testInvalidEmailAddress() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Four")
            append("email", "personfour@email")
            append("username", "personfour")
            append("password", "123")
            append("houses", "")
        }

        assertFailsWith(
            exceptionClass = RequestException::class,
            message = "Invalid value for e-mail!"
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testInvalidUsername() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Four")
            append("email", "personfour@email")
            append("username", "!personfour!")
            append("password", "123")
            append("houses", "")
        }

        assertFailsWith(
            exceptionClass = RequestException::class,
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
            append("email", "personfour@email")
            append("username", "personfour")
            append("password", "123")
            append("houses", "")
        }

        assertFailsWith(
            exceptionClass = RequestException::class,
            message = "Invalid value for name!"
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    //TODO tests for the password hashing?
}