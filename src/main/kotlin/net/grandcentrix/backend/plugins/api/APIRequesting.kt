package net.grandcentrix.backend.plugins.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import net.grandcentrix.backend.models.Book
import net.grandcentrix.backend.models.House

object APIRequesting {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

     fun fetchBooks(): List<Book>  {
        // Make a GET request to the external API
         val resJson = runBlocking {
              client.get("https://api.potterdb.com/v1/books?page[size]=25").body<JsonElement>()
         }

        val jsonData = resJson.jsonObject["data"]?.jsonArray
        val books = jsonData?.map {
            it.jsonObject["attributes"]
        }

        return books?.map { attribute ->
            val id = attribute?.jsonObject?.get("id")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val author = attribute?.jsonObject?.get("name")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val coverUrl = attribute?.jsonObject?.get("cover")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val pages = attribute?.jsonObject?.get("pages")?.toString()?.let { Json.decodeFromString<Int>(it) } ?: 0
            val releaseDate = attribute?.jsonObject?.get("release_date")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val summary = attribute?.jsonObject?.get("summary")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val slug = attribute?.jsonObject?.get("slug")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val title = attribute?.jsonObject?.get("title")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()

            // create a book with the specific attributes from the json data and add to a list of books
            Book(id, author, coverUrl, pages, releaseDate, summary, slug, title)

        }?.toList() ?: emptyList()
    }

    fun fetchHouses(): List<House> {
        // Make a GET request to the external API
        val resJson = runBlocking {
            client.get("https://wizard-world-api.herokuapp.com/Houses").body<List<JsonElement>>()
        }

        return resJson.map { house ->
            val id = Json.decodeFromString<String>(house.jsonObject["id"].toString())
            val name = Json.decodeFromString<String>(house.jsonObject["name"].toString())
            val colors = Json.decodeFromString<String>(house.jsonObject["houseColours"].toString())
            val founder = Json.decodeFromString<String>(house.jsonObject["founder"].toString())
            val animal = Json.decodeFromString<String>(house.jsonObject["animal"].toString())
            val element = Json.decodeFromString<String>(house.jsonObject["element"].toString())
            val ghost = Json.decodeFromString<String>(house.jsonObject["ghost"].toString())
            val commonRoom = Json.decodeFromString<String>(house.jsonObject["commonRoom"].toString())
            val heads: List<String> = house.jsonObject["heads"]?.jsonArray?.map { it.toString() } ?: emptyList()
            val traits: List<String> = house.jsonObject["heads"]?.jsonArray?.map { it.toString() } ?: emptyList()

            // create a book with the specific attributes from the json data and add to a list of books
            House(id, name, colors, founder, animal, element, ghost, commonRoom, heads, traits)
        }
    }

    fun fetchCharacters() {

    }

    fun fetchMovies() {

    }

    fun fetchPotions() {

    }

    fun fetchSpells() {

    }

}