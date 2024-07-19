package net.grandcentrix.backend.plugins.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import net.grandcentrix.backend.dao.DAOApi
import net.grandcentrix.backend.models.*
import net.grandcentrix.backend.plugins.GravatarProfileException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


object APIRequesting {

    private const val API_URL = "https://api.potterdb.com/v1"
    private const val API_URL_HOUSE = "https://wizard-world-api.herokuapp.com/Houses"
    val daoApi = DAOApi()

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

    fun fetchBookById(id: String): Book = runBlocking<ResponseObject<Book>> {
        client.get("${API_URL}/books/$id").body()
    }.data.attributes

    fun fetchChapters(bookId: String): List<Chapter> = runBlocking<ResponseData<Chapter>> {
        client.get("$API_URL/books/$bookId/chapters").body()
    }.data.map {
        Chapter(it.attributes.title, it.attributes.summary)
    }

    fun fetchMovieById(id: String): Movie = runBlocking<ResponseObject<Movie>> {
        client.get("${API_URL}/movies/$id").body()
    }.data.attributes


    fun fetchHouseById(id: String): House = runBlocking {
        client.get("${API_URL_HOUSE}/${id}").body()

    }


    fun fetchHouses(): List<House> = runBlocking {
            client.get("https://wizard-world-api.herokuapp.com/Houses").body()
    }

    fun fetchCharacters(pageNumber: String): List<Character> = runBlocking {
        val response = client.get("$API_URL/characters?page[number]=$pageNumber").body<ResponseData<Character>>()
        val characters = response.data.map {
            it.attributes.id = it.id
            if (it.attributes.imageUrl == null) {
                it.attributes.imageUrl = "/static/img/no_image.png"
            }
            it.attributes
        }
        // Save the characters to the database
        daoApi.saveCharacters(characters)
        characters
    }

    fun fetchCharacterById(id: String): Character = runBlocking {
        val response = client.get("$API_URL/characters/$id")
        val responseData = response.body<CharacterResponseData>()
        responseData.data.attributes
    }

    fun fetchSpellById(id: String): Spell = runBlocking {
        val response = client.get("$API_URL/spells/$id")
        val responseData = response.body<SpellResponseData>()
        responseData.data.attributes
    }

    fun fetchPotionById(id: String): Potion = runBlocking {
        val response = client.get("$API_URL/potions/$id")
        val responseData = response.body<PotionResponseData>()
        responseData.data.attributes
    }

    fun fetchCharactersPagination(pageNumber: String): PaginationData = runBlocking<ResponseData<PaginationData>> {
        client.get("$API_URL/characters?page[number]=$pageNumber").body()
    }.meta.pagination

    fun fetchMovies(): List<Movie> = runBlocking<ResponseData<Movie>> {
            client.get("$API_URL/movies").body()
        }.data.map {
            it.attributes.id = it.id
            it.attributes
        }

    fun fetchPotions(pageNumber: String): List<Potion> = runBlocking {
        val response = client.get("$API_URL/potions?page[number]=$pageNumber").body<ResponseData<Potion>>()
        val potions = response.data.map { potion ->
            potion.attributes.id = potion.id
            if (potion.attributes.imageUrl == null) {
                potion.attributes.imageUrl = "/static/img/no_image.png"
            }
            potion.attributes
        }
        // Save the potions to the database
        daoApi.savePotions(potions)
        potions
    }


    fun fetchPotionsPagination(pageNumber: String): PaginationData = runBlocking<ResponseData<PaginationData>> {
        client.get("$API_URL/potions?page[number]=$pageNumber").body()
    }.meta.pagination

    fun fetchSpells(pageNumber: String): List<Spell> = runBlocking {
        val response = client.get("$API_URL/spells?page[number]=$pageNumber").body<ResponseData<Spell>>()
        val spells = response.data.map { spell ->
            spell.attributes.id = spell.id
            if (spell.attributes.imageUrl == null) {
                spell.attributes.imageUrl = "/static/img/no_image.png"
            }
            spell.attributes
        }
        // Save the potions to the database
        daoApi.saveSpells(spells)
        spells
    }

    fun fetchSpellsPagination(pageNumber: String): PaginationData = runBlocking<ResponseData<PaginationData>> {
        client.get("$API_URL/spells?page[number]=$pageNumber").body()
    }.meta.pagination

    fun fetchGravatarProfile(email: String): GravatarProfile {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(
            email.toByteArray(StandardCharsets.UTF_8)
        )
        val emailKey: String = hex(hashBytes)

        try {
            val profile: GravatarProfile = runBlocking {
                client.get("https://api.gravatar.com/v3/profiles/${emailKey}") {
                    bearerAuth("129:gk-N0JYWAg0JaYac_Bdl3ha8nadRp1rLIasSakKhP9VZMWoQzii2yBZM2VEZrsYP")
                }.body()
            }
            return profile
        } catch (cause: Throwable) {
            throw GravatarProfileException("Gravatar API failed", cause)
        }
    }

}