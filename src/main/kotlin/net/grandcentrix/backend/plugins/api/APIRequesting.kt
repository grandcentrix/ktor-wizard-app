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
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


object APIRequesting {

    private const val apiUrl = "https://api.potterdb.com/v1"

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
         val resJson: ResponseData<Book> = runBlocking {
              client.get("$apiUrl/books").body()
         }

         val books = resJson.data.map { it.attributes }

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
            client.get("$apiUrl/characters").body()
        }

        val characters = resJson.data.map { it.attributes }
        return characters
    }
    fun fetchMovies(): List<Movie> {
        // Make a GET request to the external API
        val resJson: ResponseData<Movie> = runBlocking {
            client.get("$apiUrl/movies").body()
        }

        val movies = resJson.data.map { it.attributes }
        return movies
    }

    fun fetchPotions(): List<Potion> {
        // Make a GET request to the external API
        val resJson: ResponseData<Potion> = runBlocking {
            client.get("$apiUrl/potions").body()
        }

        val potions = resJson.data.map { it.attributes }
        return potions
    }

    fun fetchSpells(): List<Spell> {
        // Make a GET request to the external API
        val resJson: ResponseData<Spell> = runBlocking {
            client.get("$apiUrl/spells").body()
        }

        val spells = resJson.data.map { it.attributes }
        return spells
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun fetchGravatarProfile(email: String): GravatarProfile {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(
            email.toByteArray(StandardCharsets.UTF_8)
        )
        val emailKey: String = hashBytes.toHexString()

        val profile: GravatarProfile = runBlocking {
            client.get("https://api.gravatar.com/v3/profiles/${emailKey}") {
                bearerAuth("129:gk-N0JYWAg0JaYac_Bdl3ha8nadRp1rLIasSakKhP9VZMWoQzii2yBZM2VEZrsYP")
            }.body()
        } ?: throw NoGravatarProfile("User email doesn't have a gravatar profile")

        return profile
    }

}