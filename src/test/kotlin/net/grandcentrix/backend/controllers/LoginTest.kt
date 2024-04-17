package net.grandcentrix.backend.controllers

import io.ktor.server.auth.*
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.repository.UserManager.Companion.UserManagerInstance
import java.io.File
import kotlin.test.*

class LoginTest {
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
    fun testVerifyLoginSuccess() {
        // get existing user and password
        val username = UserManagerInstance.getAll().first().username
        val password = UserManagerInstance.getAll().first().password
        // create credentials
        val credentials = UserPasswordCredential(username, password)

        assertTrue(LoginInstance.verifyLogin(credentials))
    }

    @Test
    fun testVerifyLoginFails() {
        // non-matching credentials
        val username = UserManagerInstance.getAll().first().username
        val password = UserManagerInstance.getAll().last().password
        val credentials = UserPasswordCredential(username, password)

        assertFalse(LoginInstance.verifyLogin(credentials))
    }
}