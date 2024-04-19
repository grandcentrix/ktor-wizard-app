package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.plugins.*
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.UserAlreadyExistsException
import org.junit.Test
import java.io.File
import kotlin.test.*

class SignupTest {

    companion object {
        private const val FILE_NAME = "src/main/resources/testFile.json"
    }

    @BeforeTest
    fun beforeTest() {
        // copy all users from users.json to a testFile.json
        val users = UserManagerInstance.getAll()
        val usersJson = Json.encodeToJsonElement<List<User>>(users).toString()
        File(FILE_NAME).writeText(usersJson)

        // mock the repository class and mock the return file to be the test file
        mockkObject(UserManagerInstance, recordPrivateCalls = true)
        every { UserManagerInstance["getFile"]() } returns File(FILE_NAME)
    }

    @AfterTest
    fun afterTest() {
        unmockkAll()
        // reset test file
        File(FILE_NAME).writeText("[]")
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
        val duplicatedUsername = UserManagerInstance.getAll().first().username
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
        val duplicatedEmail = UserManagerInstance.getAll().first().email
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
        val user = UserManagerInstance.getItem(formParameters["username"]!!)

        assertNull(user!!.house)
    }

    @Test
    fun testCreateUserWithHouse() {
        val formParameters = Parameters.build {
            append("name", "Person")
            append("surname", "Two")
            append("email", "persontwo@email.com")
            append("username", "persontwo")
            append("password", "123")
            append("house", "Gryffindor")
        }

        SignupInstance.createUser(formParameters)
        val user = UserManagerInstance.getItem(formParameters["username"]!!)

        assertNotNull(user!!.house)
        assertIs<House>(user.house)
        assertEquals(
            expected = formParameters["house"]!!,
            actual = user.house!!.name
        )
    }

}