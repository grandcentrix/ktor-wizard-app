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

    fun fetchBooks(): List<Book> = runBlocking {
        val response = client.get("$API_URL/books").body<ResponseData<Book>>()
        val book = response.data.map { book ->
            book.attributes.id = book.id
            if (book.attributes.coverUrl == null) {
                book.attributes.coverUrl = "/static/img/no_image.png"
            }
            book.attributes
        }
        // Save the movies to the database
        daoApi.saveBooks(book)
        book
    }

    fun fetchBookById(id: String): Book = runBlocking {
        val book = daoApi.getBookByID(id)
        if (book != null) {
            return@runBlocking book
        }

        val response = client.get("$API_URL/books/$id")
        val responseData = response.body<BookResponseData>()
        val bookAttributes = responseData.data.attributes
        // Save the book to the database
        daoApi.saveBooks(listOf(bookAttributes))
        return@runBlocking bookAttributes
    }

    fun fetchChapters(bookId: String): List<Chapter> = runBlocking<ResponseData<Chapter>> {
        client.get("$API_URL/books/$bookId/chapters").body()
    }.data.map {
        Chapter(it.attributes.title, it.attributes.summary)
    }

    fun fetchMovieById(id: String): Movie = runBlocking {
        val movie = daoApi.getMovieByID(id)
        if (movie != null) {
            return@runBlocking movie
        }

        val response = client.get("$API_URL/movies/$id")
        val responseData = response.body<MovieResponseData>()
        val movieAttributes = responseData.data.attributes
        // Save the movie to the database
        daoApi.saveMovies(listOf(movieAttributes))
        return@runBlocking movieAttributes
    }


    fun fetchHouseById(id: String): House = runBlocking {
        client.get("${API_URL_HOUSE}/${id}").body()

    }


    fun fetchHouses(): List<House> = runBlocking {
            client.get("https://wizard-world-api.herokuapp.com/Houses").body()
    }

    suspend fun fetchCharacters(pageNumber: Int): List<Character> {
        val cachedCharacters = daoApi.getCharactersByPage(pageNumber, pageSize = 100)
        if (cachedCharacters.isNotEmpty()) {
            return cachedCharacters
        }

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
        return characters
    }

    suspend fun fetchCharacterById(id: String): Character {
        val character = daoApi.getCharacterByID(id)
        if (character!= null) {
            return character
        }

        val response = client.get("$API_URL/characters/$id")
        val responseData = response.body<CharacterResponseData>()
        val characterAttributes = responseData.data.attributes
        // Save the character to the database
        if (character != null) {
            daoApi.saveCharacters(character)
        }
        return characterAttributes
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

    fun fetchMovies(pageNumber: String = "1"): List<Movie> = runBlocking {
        val response = client.get("$API_URL/movies?page[number]=$pageNumber").body<ResponseData<Movie>>()
        val movies = response.data.map { movie ->
            movie.attributes.id = movie.id
            if (movie.attributes.posterUrl == null) {
                movie.attributes.posterUrl = "/static/img/no_image.png"
            }
            movie.attributes
        }
        // Save the movies to the database
        daoApi.saveMovies(movies)
        movies
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