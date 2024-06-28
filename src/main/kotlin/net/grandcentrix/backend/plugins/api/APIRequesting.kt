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

     fun fetchBooks(): List<Book> = runBlocking<ResponseData<Book>> {
              client.get("$API_URL/books").body()
         }.data.map {
             it.attributes.id = it.id
             it.attributes
         }

    fun fetchHouses(): List<House> = runBlocking {
            client.get("https://wizard-world-api.herokuapp.com/Houses").body()
    }

    fun fetchCharacters(): List<Character> = runBlocking<ResponseData<Character>> {
            client.get("$API_URL/characters").body()
        }.data.map {
            it.attributes.id = it.id
            it.attributes
        }

    fun fetchMovies(): List<Movie> = runBlocking<ResponseData<Movie>> {
            client.get("$API_URL/movies").body()
        }.data.map {
            it.attributes.id = it.id
            it.attributes
        }

    fun fetchPotions(): List<Potion> = runBlocking<ResponseData<Potion>> {
            client.get("$API_URL/potions").body()
        }.data.map {
            it.attributes.id = it.id
            it.attributes
        }


    fun fetchSpells(): List<Spell> = runBlocking<ResponseData<Spell>> {
            client.get("$API_URL/spells").body()
        }.data.map {
            it.attributes.id = it.id
            it.attributes
        }
}