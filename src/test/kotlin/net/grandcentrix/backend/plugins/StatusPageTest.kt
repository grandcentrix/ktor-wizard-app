package net.grandcentrix.backend.plugins

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import net.grandcentrix.backend.dao.daoUsers
import org.jetbrains.exposed.sql.Database
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class StatusPageTest {

    companion object {
        private val config = ApplicationConfig("./src/main/resources/application.conf")
        val driverClassName = config.property("storage.driverClassName").getString()
        val url = config.property("storage.jdbcURL").getString()
    }
    
    @Test
    fun testAuthorizationException() {
        //TODO
    }

    @Test
    fun testNotAuthorized() = testApplication {
        // Retrieve username from storage for test purpose
        Database.connect(url, driverClassName)

        // non-matching existing credentials
        val username = "user"
        val password = "12345"

        // login attempt
        val response = client.post("/login") {
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            setBody(listOf("username" to username, "password" to password).formUrlEncode())
        }

        // should redirect to login in case login authentication fails
        val location = response.headers["Location"].toString()

        assertContains("/login", location)
    }

    @Test
    fun testUserAlreadyExistsException() = testApplication {
        // Retrieve username from storage for test purpose
        Database.connect(url, driverClassName)

        // existing credentials
        val username = daoUsers.getAll().first().username
        val password = daoUsers.getAll().first().password.toString()

        val response = client.post("/signup") {
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            setBody(listOf(
                "name" to "Person",
                "surname" to "One",
                "email" to "personone@mail.com",
                "username" to username,
                "password" to password,
                "house" to ""
            ).formUrlEncode())
        }

        // should redirect to signup in case login authentication fails
        val location = response.headers["Location"].toString()

        assertContains("/signup", location)
    }

    @Test
    fun testMissingRequestParameterException() = testApplication {
        val response = client.post("/signup") {
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            setBody(listOf(
                "name" to "",
                "surname" to "One",
                "email" to "personone@mail.com",
                "username" to "person",
                "password" to "123",
                "house" to ""
            ).formUrlEncode())
        }

        val location = response.headers["Location"].toString()

        assertContains("/signup", location)
    }

    @Test
    fun testInvalidValueException() = testApplication {
        val response = client.post("/signup") {
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            setBody(listOf(
                "name" to "Person",
                "surname" to "One",
                "email" to "123",
                "username" to "person",
                "password" to "123",
                "house" to ""
            ).formUrlEncode())
        }

        val location = response.headers["Location"].toString()

        assertContains("/signup", location)
    }

    @Test
    fun testServerError() = testApplication {
        //TODO
    }

    @Test
    fun testNotFound() = testApplication {
        val response = client.get("/somePage")

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), "404 - Not found")
    }
}