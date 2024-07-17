package net.grandcentrix.backend

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockkObject
import net.grandcentrix.backend.dao.DatabaseSingleton
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull


class RoutingTest {

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
    fun testLoginWithValidCredentials() = testApplication {
        // Sending a POST request to "/login" endpoint with valid username and password
        val response = client.post("/login") {
            // Setting the content type header to application/x-www-form-urlencoded
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            // Setting the request body with username and password encoded in form-url-encoded format
            setBody(listOf("username" to "personone", "password" to "123").formUrlEncode())
        }
        // Asserting that the response status code is HttpStatusCode.Found (302)
        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun getLoginPage() = testApplication {
        // Sending a GET request to "/login" endpoint
        val response = client.get("/login")
        // Asserting that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.OK, response.status)
    }


    @Test
    fun testLoginWithInvalidCredentials() = testApplication {
        // Sending a POST request to "/login" endpoint with invalid username and password
        val response = client.post("/login") {
            // Setting the content type header to application/x-www-form-urlencoded
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            // Setting the request body with invalid username and password encoded in form-url-encoded format
            setBody(listOf("username" to "invaliduser", "password" to "pass").formUrlEncode())
        }
        // Extracting the 'Location' header from the response, which contains the redirected URL
        val location = response.headers["Location"].toString()
        // Sending a GET request to the URL obtained from the 'Location' header
        val redirectedResponse = client.get(location).bodyAsText()
        // Asserting that the response status code is HttpStatusCode.Found (302)
        assertEquals(HttpStatusCode.Found, response.status)
    }

    @Test
    fun getSignUpPage() = testApplication {
        // Sending a GET request to "/signup" endpoint
        val response = client.get("/signup")
        // Asserting that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.OK, response.status)
    }

    // Test case to simulate a successful signup
//    @Test
//    fun createAccountWithSuccess() = testApplication {
//
//        // Simulating form parameters for signup
//        val formParameters = Parameters.build {
//            append("name", "Person")
//            append("surname", "Two")
//            append("email", "persontwo@email.com")
//            append("username", "person")
//            append("password", "123")
//            append("house", "0367baf3-1cb6-4baf-bede-48e17e1cd005")
//        }
//
//        // Sending a POST request to "/signup" endpoint with form parameters
//        val signupResponse = client.post("/signup") {
//            // Setting the content type header to application
//            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
//            // Setting the request body with form parameters encoded in form-url-encoded format
//            setBody(formParameters.formUrlEncode())
//        }
//
//        val location = signupResponse.headers["Location"].toString()
//
//        // Asserting that the Location header redirects to "/login"
//        assertEquals("/login", location)
//
//        // Asserting that the signup response status code is Status Code 200
//        assertEquals(HttpStatusCode.Found, signupResponse.status)
//    }

    @Test
    fun createAccountWithInvalidSignup() = testApplication {
        // Simulating form parameters for signup with invalid data
        val formParameters = Parameters.build {
            // Appending all parameters with one being empty
            append("name", "")
            append("surname", "Doe")
            append("email", "testemail@email.com")
            append("username", "testuser")
            append("password", "testpassword")
        }

        // Sending a POST request to "/signup" endpoint with form parameters
        val signupResponse = client.post("/signup") {
            // Setting the content type header to application
            header(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            // Setting the request body with form parameters encoded in form-url-encoded format
            setBody(formParameters.formUrlEncode())
        }

        assertEquals(HttpStatusCode.OK, signupResponse.status)

        // Asserting that the Location header is not present, indicating that it's not a redirect
        assertFalse(signupResponse.headers.contains("Location"))


    }

    @Test
    fun accessProfilePageNotAuthenticated() = testApplication {

        val response = client.get("/profile") {
            // No session cookie added
        }

        // Assert that the response status code is HttpStatusCode.Found (302),
        // indicating a redirection to another page
        assertEquals(HttpStatusCode.OK, response.status)

        // Assert that the URL of the request is "/login", indicating redirection
        val url = response.request.url.encodedPath
        assertEquals("/login", url)
    }

    @Test
    fun accessProfilePageAuthenticated() = testApplication {
        // Retrieve username from storage
        val username = daoUsers.getAll().firstOrNull()?.username ?: ""

        // Send a GET request to "/profile" endpoint with authenticated session
        val response = client.get("/profile") {
            // Add a session cookie to the request header
            cookie("auth-session", username)
        }

        // Assert that the response status code is HttpStatusCode.OK (200)
        assertEquals(HttpStatusCode.OK, response.status)

        // Assert that the response location header is null
        assertNull(response.headers["Location"])
    }

}