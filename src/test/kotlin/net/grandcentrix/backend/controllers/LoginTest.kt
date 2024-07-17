package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.mockk.every
import io.mockk.mockkObject
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.dao.DatabaseSingleton
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LoginTest {

    @BeforeTest
    fun beforeTest() {
        // set a test database using a configuration file for tests
        mockkObject(DatabaseSingleton)
        every {
            DatabaseSingleton.init(ApplicationConfig("application.conf"))
        } returns DatabaseSingleton.init(ApplicationConfig("testApplication.conf"))

        // must add user through SignupInstance to hash the password
        val userParameters = Parameters.build {
            append("name", "Person")
            append("surname", "One")
            append("email", "personone@email.com")
            append("username", "personone")
            append("password", "123")
            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
        }

        SignupInstance.createUser(userParameters)
    }

    @Test
    fun testVerifyLoginSuccess() {
        val credentials = UserPasswordCredential("personone", "123")
        assertTrue(LoginInstance.verifyLogin(credentials))
    }

    @Test
    fun testVerifyLoginFails() {
        // non-matching credentials
        val credentials = UserPasswordCredential("personone", "102030")
        assertFalse(LoginInstance.verifyLogin(credentials))
    }
}