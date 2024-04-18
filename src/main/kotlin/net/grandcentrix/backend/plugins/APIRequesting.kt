package net.grandcentrix.backend.plugins

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

suspend fun requestBooks(): JsonElement {
    // Create a Ktor HTTP client with CIO engine and JSON feature
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    try {
        // Make a GET request to the external API
        val resJson: JsonElement = client.get("https://api.potterdb.com/v1/books?page[size]=25").body()
        return resJson

    } finally {
        // Close the HTTP client
        client.close()
    }

}