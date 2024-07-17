package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.config.*
import io.mockk.every
import io.mockk.mockkObject
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.RequestException
import net.grandcentrix.backend.plugins.SignupException
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

class SignupTest {

    @BeforeTest
    fun beforeTest() {
        // set a test database using a configuration file for tests
        mockkObject(DatabaseSingleton)
        every {
            DatabaseSingleton.init(ApplicationConfig("application.conf"))
        } returns DatabaseSingleton.init(ApplicationConfig("testApplication.conf"))

        val user = User (
            "Person",
            "One",
            "personone@email.com",
            "personone",
            "0367baf3-1cb6-4baf-bede-48e17e1cd005",
            "123"
        )

        daoUsers.addItem(user)
    }

    @Test
    fun testCreateUserWithMissingFields() {
        val formParameters = Parameters.build {
            append("name", "")
            append("surname", "")
            append("email", "persontwo@email.com")
            append("username", "")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
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
            append("surname", "Two")
            append("email", "persontwo@email.com")
            append("username", "personone")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        assertFailsWith(
            exceptionClass = SignupException::class,
            message = "Failed to create account. E-mail and/or username must be taken."
        )   {
                SignupInstance.createUser(formParameters)
            }
    }

    @Test
    fun testCreateUserWitDuplicatedEmail() {
        val duplicatedEmail = daoUsers.getAll().first().email
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Two")
            append("email", duplicatedEmail)
            append("username", "persontwo")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        assertFailsWith(
            exceptionClass = SignupException::class,
            message = "Failed to create account. E-mail and/or username must be taken."
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testCreateUserWithoutHouse() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Two")
            append("email", "persontwo@email.com")
            append("username", "persontwo")
            append("password", "123")
            append("house", "")
        }

        assertFailsWith(
            exceptionClass = RequestException::class,
            message = "Missing required fields!"
        ) {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testInvalidEmailAddress() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Two")
            append("email", "persontwo@email")
            append("username", "persontwo")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        assertFailsWith(
            exceptionClass = SignupException::class,
            message = "Must be a valid format for e-mail address."
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testEmailAddressWithInvalidCharacters() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Two")
            append("email", "persontwo??email")
            append("username", "persontwo")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        assertFailsWith(
            exceptionClass = SignupException::class,
            message = "E-mail contain invalid characters."
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testUsernameWithInvalidCharacters() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Two")
            append("email", "persontwo@email")
            append("username", "!persontwo!")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        assertFailsWith(
            exceptionClass = SignupException::class,
            message = "Username can only contain alphanumeric, underscore and point characters."
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testInvalidUsername() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Two")
            append("email", "persontwo@email")
            append("username", "_persontwo")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        assertFailsWith(
            exceptionClass = SignupException::class,
            message = "Username can't start with non-alphanumeric characters or be more than 25 characters."
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testInvalidName() {
        val formParameters = Parameters.build {
            append("name", "1234")
            append("surname", "Person")
            append("email", "persontwo@email")
            append("username", "persontwo")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        assertFailsWith(
            exceptionClass = SignupException::class,
            message = "Name and surname must contain only letters."
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    @Test
    fun testInvalidSurname() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Person 22")
            append("email", "persontwo@email")
            append("username", "persontwo")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        assertFailsWith(
            exceptionClass = SignupException::class,
            message = "Name and surname must contain only letters."
        )   {
            SignupInstance.createUser(formParameters)
        }
    }

    //TODO tests for the password hashing?
}