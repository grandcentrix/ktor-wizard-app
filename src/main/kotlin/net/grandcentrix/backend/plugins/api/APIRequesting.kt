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
import net.grandcentrix.backend.models.*
import net.grandcentrix.backend.repository.HouseRepository.Companion.HouseRepositoryInstance

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
              client.get("https://api.potterdb.com/v1/books").body<JsonElement>()
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

    fun fetchCharacters(): List<Character> {
        // Make a GET request to the external API
        val resJson = runBlocking {
            client.get("https://api.potterdb.com/v1/characters").body<JsonElement>()
        }

        val jsonData = resJson.jsonObject["data"]?.jsonArray
        val characters = jsonData?.map {
            it.jsonObject["attributes"]
        }

        return characters?.map { attribute ->
            val id = attribute?.jsonObject?.get("id")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val name = attribute?.jsonObject?.get("name")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val aliasname = attribute?.jsonObject?.get("alias_names")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val animagus = attribute?.jsonObject?.get("animagus")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val boggart = attribute?.jsonObject?.get("boggart")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val patronus = attribute?.jsonObject?.get("patronus")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val birth = attribute?.jsonObject?.get("born")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val death = attribute?.jsonObject?.get("died")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val familyMembers = attribute?.jsonObject?.get("family_members")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val house = attribute?.jsonObject?.get("house")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val imageUrl = attribute?.jsonObject?.get("image")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val jobs = attribute?.jsonObject?.get("jobs")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val nationality = attribute?.jsonObject?.get("nationality")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val slug = attribute?.jsonObject?.get("slug")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val specie = attribute?.jsonObject?.get("specie")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val titles = attribute?.jsonObject?.get("titles")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val wands = attribute?.jsonObject?.get("wands")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()

            // create a book with the specific attributes from the json data and add to a list of books
            Character(
                id,
                name,
                aliasname,
                animagus,
                boggart,
                patronus,
                birth,
                death,
                familyMembers,
                HouseRepositoryInstance.getItem(house),
                imageUrl,
                jobs,
                nationality,
                slug,
                specie,
                titles,
                wands
            )
        }?.toList() ?: emptyList()
    }

    fun fetchMovies(): List<Movie> {
        // Make a GET request to the external API
        val resJson = runBlocking {
            client.get("https://api.potterdb.com/v1/movies").body<JsonElement>()
        }

        val jsonData = resJson.jsonObject["data"]?.jsonArray
        val movies = jsonData?.map {
            it.jsonObject["attributes"]
        }

        return movies?.map { attribute ->
            val id = attribute?.jsonObject?.get("id")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val title = attribute?.jsonObject?.get("title")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val boxOffice = attribute?.jsonObject?.get("box_office")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val budget = attribute?.jsonObject?.get("budget")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val cinematographers = attribute?.jsonObject?.get("cinematographers")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val directors = attribute?.jsonObject?.get("directors")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val screenwriters = attribute?.jsonObject?.get("screenwriters")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val posterUrl = attribute?.jsonObject?.get("poster")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val producers = attribute?.jsonObject?.get("producers")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val rating = attribute?.jsonObject?.get("rating")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val releaseDate = attribute?.jsonObject?.get("release_date")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val duration = attribute?.jsonObject?.get("id")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val slug = attribute?.jsonObject?.get("slug")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val trailer = attribute?.jsonObject?.get("trailer")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()

            Movie(
                id,
                title,
                boxOffice,
                budget,
                cinematographers,
                directors,
                screenwriters,
                posterUrl,
                producers,
                rating,
                releaseDate,
                duration,
                slug,
                trailer
            )
        }?.toList() ?: emptyList()
    }

    fun fetchPotions(): List<Potion> {
        // Make a GET request to the external API
        val resJson = runBlocking {
            client.get("https://api.potterdb.com/v1/potions").body<JsonElement>()
        }

        val jsonData = resJson.jsonObject["data"]?.jsonArray
        val potions = jsonData?.map {
            it.jsonObject["attributes"]
        }

        return potions?.map { attribute ->
            val id = attribute?.jsonObject?.get("id")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val name = attribute?.jsonObject?.get("name")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val characteristics = attribute?.jsonObject?.get("characteristics")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val difficulty = attribute?.jsonObject?.get("difficulty")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val effect = attribute?.jsonObject?.get("effect")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val imageUrl = attribute?.jsonObject?.get("image")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val inventors = attribute?.jsonObject?.get("inventors")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val ingredients = attribute?.jsonObject?.get("ingredients")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val manufacturers = attribute?.jsonObject?.get("manufacturers")?.toString()?.let { Json.decodeFromString<List<String>>(it) } ?: listOf()
            val sideEffects = attribute?.jsonObject?.get("side_effects")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val slug = attribute?.jsonObject?.get("slug")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val time = attribute?.jsonObject?.get("time")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()

            Potion(
                id,
                name,
                characteristics,
                difficulty,
                effect,
                imageUrl,
                inventors,
                ingredients,
                manufacturers,
                sideEffects,
                slug,
                time
            )
        }?.toList() ?: emptyList()
    }

    fun fetchSpells(): List<Spell> {
        // Make a GET request to the external API
        val resJson = runBlocking {
            client.get("https://api.potterdb.com/v1/spells").body<JsonElement>()
        }

        val jsonData = resJson.jsonObject["data"]?.jsonArray
        val spells = jsonData?.map {
            it.jsonObject["attributes"]
        }

        return spells?.map { attribute ->
            val id = attribute?.jsonObject?.get("id")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val name = attribute?.jsonObject?.get("name")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val category = attribute?.jsonObject?.get("category")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val creator = attribute?.jsonObject?.get("creator")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val effect = attribute?.jsonObject?.get("effect")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val hand = attribute?.jsonObject?.get("hand")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val imageUrl = attribute?.jsonObject?.get("image")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val incantation = attribute?.jsonObject?.get("incantation")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val light = attribute?.jsonObject?.get("light")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val slug = attribute?.jsonObject?.get("slug")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()
            val type = attribute?.jsonObject?.get("type")?.toString()?.let { Json.decodeFromString<String>(it) } ?: String()

            Spell(
                id,
                name,
                category,
                creator,
                effect,
                hand,
                imageUrl,
                incantation,
                light,
                slug,
                type
            )
        }?.toList() ?: emptyList()
    }

}