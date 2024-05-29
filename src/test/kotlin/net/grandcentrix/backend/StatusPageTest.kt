package net.grandcentrix.backend

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import net.grandcentrix.backend.dao.daoUsers
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class StatusPageTest {
    @Test
    fun testAuthorizationException() {

    }

    @Test
    fun testNotAuthorized() = testApplication {
        // non-matching existing credentials
        val username = daoUsers.getAll().first().username
        val password = daoUsers.getAll().last().password.toString()

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
    fun testServerError() = testApplication {

    }

    @Test
    fun testNotFound() = testApplication {
        val response = client.get("/somePage") {
            contentType(ContentType.Application.GZip)
        }
        val test3 = response.body<ByteArray>().decodeToString()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), "Oops! It")
    }
}