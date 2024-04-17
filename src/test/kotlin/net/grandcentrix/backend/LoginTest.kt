package net.grandcentrix.backend

import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import io.ktor.client.request.*
import io.ktor.client.statement.*

class LoginTest {

        // Test case to simulate a login attempt with valid credentials
        @Test
        fun sadfad() = testApplication {
            // Sending a POST request to "/login" endpoint with valid username and password
            val response = client.post("/login") {
                // Setting the content type header to application/x-www-form-urlencoded
                header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                // Setting the request body with username and password encoded in form-url-encoded format
                setBody(listOf("username" to "123", "password" to "123").formUrlEncode())
            }
            // Asserting that the response status code is HttpStatusCode.Found (302)
            assertEquals(HttpStatusCode.Found, response.status)
        }

        // Test case to simulate a login attempt with invalid credentials
        @Test
        fun afafa() = testApplication {
            // Sending a POST request to "/login" endpoint with invalid username and password
            val response = client.post("/login") {
                // Setting the content type header to application/x-www-form-urlencoded
                header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                // Setting the request body with invalid username and password encoded in form-url-encoded format
                setBody(listOf("username" to "gfsgs", "password" to "gdsgs").formUrlEncode())
            }
            // Extracting the 'Location' header from the response, which contains the redirection URL
            val location = response.headers["Location"].toString()
            // Sending a GET request to the URL obtained from the 'Location' header
            val redirectedResponse = client.get(location).bodyAsText()
            // Asserting that the redirected response body contains the expected message "Login not authorized"
            assertContains(redirectedResponse, "Login not authorized")
            // Asserting that the response status code is HttpStatusCode.Found (302)
            assertEquals(HttpStatusCode.Found, response.status)
        }
    }
