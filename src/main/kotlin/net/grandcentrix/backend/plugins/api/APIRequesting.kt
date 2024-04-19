package net.grandcentrix.backend.plugins.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import net.grandcentrix.backend.models.Book

object APIRequesting {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

     suspend fun fetchBooks(): List<Book> {
        // Make a GET request to the external API
        val resJson: JsonElement = client.get("https://api.potterdb.com/v1/books?page[size]=25").body()

        val jsonData = resJson.jsonObject["data"]?.jsonArray
        val attributes = jsonData?.map {
            it.jsonObject["attributes"]
        }

        return attributes?.map { attribute ->
            val id = attribute?.jsonObject?.get("id").toString()
            val author = attribute?.jsonObject?.get("author").toString()
            val coverUrl = attribute?.jsonObject?.get("cover").toString()
            val pages = attribute?.jsonObject?.get("pages").toString().toInt()
            val releaseDate = attribute?.jsonObject?.get("release_date").toString()
            val summary = attribute?.jsonObject?.get("summary").toString()
            val slug = attribute?.jsonObject?.get("slug").toString()
            val title = attribute?.jsonObject?.get("title").toString()

            // create a book with the specific attributes from the json data and add to a list of books
            Book(id, author, coverUrl, pages, releaseDate, summary, slug, title)

        }?.toList() ?: emptyList()
    }
}