package net.grandcentrix.backend

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import net.grandcentrix.backend.models.User
import org.junit.Test
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RoutingTest {

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
    fun testLoginWithValidCredentials() = testApplication {
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
        // Asserting that the redirected response body contains the expected message "Login not authorized"
        assertContains(redirectedResponse, "Login is invalid!")
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
    @Test
    fun createAccountWithSuccess() = testApplication {
        // Simulating form parameters for signup
        val formParameters = Parameters.build {
            append("name", "John")
            append("surname", "Doe")
            append("email", "john.doe@example.com")
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

        val location =  signupResponse.headers["Location"].toString()

        // Asserting that the signup response status code is HttpStatusCode.Found (302)
        // as it should redirect to "/login"
        assertEquals(HttpStatusCode.Found, signupResponse.status)

        // Asserting that the Location header redirects to "/login"
        assertEquals("/login", location)
    }
}