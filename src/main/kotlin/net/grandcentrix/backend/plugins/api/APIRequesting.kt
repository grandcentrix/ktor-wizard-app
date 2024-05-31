package net.grandcentrix.backend.plugins.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import net.grandcentrix.backend.models.Book
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.models.ResponseData

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
         val resJson: ResponseData = runBlocking {
              client.get("$API_URL/books").body()
         }

         val books = resJson.data.map {
             Book(
                 it.id,
                 it.attributes["author"]!!,
                 it.attributes["cover"],
                 it.attributes["pages"]!!.toInt(),
                 it.attributes["release_date"]!!,
                 it.attributes["summary"]!!,
                 it.attributes["slug"]!!,
                 it.attributes["title"]!!,
             )
         }.toList()

         return books
     }

    fun fetchHouses(): List<House> {
        // Make a GET request to the external API
        val resJson: List<House> = runBlocking {
            client.get("https://wizard-world-api.herokuapp.com/Houses").body()
        }

        return resJson
    }

//    fun fetchCharacters(): List<Character> {
//        // Make a GET request to the external API
//        val resJson: ResponseData<Character> = runBlocking {
//            client.get("$apiUrl/characters").body()
//        }
//
//        val characters = resJson.data.map { it.attributes }
//        return characters
//    }
//
//    fun fetchMovies(): List<Movie> {
//        // Make a GET request to the external API
//        val resJson: ResponseData<Movie> = runBlocking {
//            client.get("$apiUrl/movies").body()
//        }
//
//        val movies = resJson.data.map { it.attributes }
//        return movies
//    }
//
//    fun fetchPotions(): List<Potion> {
//        // Make a GET request to the external API
//        val resJson: ResponseData<Potion> = runBlocking {
//            client.get("$apiUrl/potions").body()
//        }
//
//        val potions = resJson.data.map { it.attributes }
//        return potions
//    }
//
//    fun fetchSpells(): List<Spell> {
//        // Make a GET request to the external API
//        val resJson: ResponseData<Spell> = runBlocking {
//            client.get("$apiUrl/spells").body()
//        }
//
//        val spells = resJson.data.map { it.attributes }
//        return spells
//    }

}