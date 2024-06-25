package net.grandcentrix.backend.plugins.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import net.grandcentrix.backend.models.*

object APIRequesting {

    private const val API_URL = "https://api.potterdb.com/v1"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

     fun fetchBooks(): List<Book> {

        // Make a GET request to the external API
         val resJson: ResponseData<Book> = runBlocking {
              client.get("$API_URL/books").body()
         }

         val booksIDs = resJson.data.map { it.id }
         val books = resJson.data.map { it.attributes }

         for (i in booksIDs.indices) {
            books[i].id = booksIDs[i]
         }

         return books
     }

    fun fetchHouses(): List<House> {
        // Make a GET request to the external API
        val resJson: List<House> = runBlocking {
            client.get("https://wizard-world-api.herokuapp.com/Houses").body()
        }

        return resJson
    }

    fun fetchCharacters(): List<Character> {
        // Make a GET request to the external API
        val resJson: ResponseData<Character> = runBlocking {
            client.get("$API_URL/characters").body()
        }

        val charactersIDs = resJson.data.map { it.id }
        val characters = resJson.data.map { it.attributes }

        for (i in charactersIDs.indices) {
            characters[i].id = charactersIDs[i]
        }

        return characters
    }

    fun fetchMovies(): List<Movie> {
        // Make a GET request to the external API
        val resJson: ResponseData<Movie> = runBlocking {
            client.get("$API_URL/movies").body()
        }

        val moviesIDs = resJson.data.map { it.id }
        val movies = resJson.data.map { it.attributes }

        for (i in moviesIDs.indices) {
            movies[i].id = moviesIDs[i]
        }

        return movies
    }

    fun fetchPotions(): List<Potion> {
        // Make a GET request to the external API
        val resJson: ResponseData<Potion> = runBlocking {
            client.get("$API_URL/potions").body()
        }

        val potionsIDs = resJson.data.map { it.id }
        val potions = resJson.data.map { it.attributes }

        for (i in potionsIDs.indices) {
            potions[i].id = potionsIDs[i]
        }

        return potions
    }

    fun fetchSpells(): List<Spell> {
        // Make a GET request to the external API
        val resJson: ResponseData<Spell> = runBlocking {
            client.get("$API_URL/spells").body()
        }

        val spellsIDs = resJson.data.map { it.id }
        val spells = resJson.data.map { it.attributes }

        for (i in spellsIDs.indices) {
            spells[i].id = spellsIDs[i]
        }

        return spells
    }

}