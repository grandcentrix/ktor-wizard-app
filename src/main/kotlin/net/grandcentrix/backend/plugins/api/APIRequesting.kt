package net.grandcentrix.backend.plugins.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import net.grandcentrix.backend.models.*
import net.grandcentrix.backend.plugins.GravatarProfileException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

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

    fun getBookById(id: String): Book = runBlocking<ResponseObject<Book>> {
        client.get("${API_URL}/books/$id").body()
    }.data.attributes

    fun fetchHouses(): List<House> = runBlocking {
        client.get("https://wizard-world-api.herokuapp.com/Houses").body()
    }

    fun getHouseById(id: String): List<House> = runBlocking {
        client.get("https://wizard-world-api.herokuapp.com/Houses/$id").body()
    }

    fun fetchCharacters(): List<Character> = runBlocking<ResponseData<Character>> {
            client.get("$API_URL/characters").body()
        }.data.map {
            it.attributes.id = it.id
            it.attributes
        }

    fun getCharacterById(id: String): Character = runBlocking<ResponseObject<Character>> {
        client.get("${API_URL}/characters/$id").body()
    }.data.attributes

    fun fetchMovies(): List<Movie> = runBlocking<ResponseData<Movie>> {
            client.get("$API_URL/movies").body()
        }.data.map {
            it.attributes.id = it.id
            it.attributes
        }

    fun getMovieById(id: String): Movie = runBlocking<ResponseObject<Movie>> {
        client.get("${API_URL}/movies/$id").body()
    }.data.attributes

    fun fetchPotions(): List<Potion> = runBlocking<ResponseData<Potion>> {
            client.get("$API_URL/potions").body()
        }.data.map {
            it.attributes.id = it.id
            it.attributes
        }

    fun getPotionById(id: String): Potion = runBlocking<ResponseObject<Potion>> {
        client.get("${API_URL}/potions/$id").body()
    }.data.attributes

    fun fetchSpells(): List<Spell> = runBlocking<ResponseData<Spell>> {
            client.get("$API_URL/spells").body()
        }.data.map {
            it.attributes.id = it.id
            it.attributes
        }

    fun getSpellById(id: String): Spell = runBlocking<ResponseObject<Spell>> {
        client.get("${API_URL}/spells/$id").body()
    }.data.attributes

    fun fetchGravatarProfile(email: String): GravatarProfile {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(
            email.toByteArray(StandardCharsets.UTF_8)
        )
        val emailKey: String = hex(hashBytes)

        try {
            val profile: GravatarProfile = runBlocking {
                val request = client.get("https://api.gravatar.com/v3/profiles/${emailKey}") {
                    bearerAuth("129:gk-N0JYWAg0JaYac_Bdl3ha8nadRp1rLIasSakKhP9VZMWoQzii2yBZM2VEZrsYP")
                }

                when (request.status) {
                    HttpStatusCode.NotFound -> {
                        throw GravatarProfileException("404 - Profile not found")
                    }
                    HttpStatusCode.TooManyRequests -> {
                        throw GravatarProfileException("429 - API rate limit exceeded")
                    }
                    HttpStatusCode.InternalServerError -> {
                        throw GravatarProfileException("500 - Profile not found")
                    }
                    else -> request.body()
                }
            }

            return profile
        } catch (cause: Throwable) {
            throw GravatarProfileException("Gravatar API failed", cause)
        }
    }
}