package net.grandcentrix.backend.plugins

import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.controllers.getProfilePicture
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.House
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooks
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchCharacters
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchMovies
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchPotions
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchSpells
import net.grandcentrix.backend.plugins.api.APIRequesting.getHouseById

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
}

fun getUserHouse(username: String): House {
    val houseId = daoUsers.getHouse(username)
    return getHouseById(houseId)
}

suspend fun ApplicationCall.getTemplate(
    templateName: String,
    userSession: UserSession? = null,
    valueMap: MutableMap<String,Any?>
) = respondTemplate(
        templateName,
        valueMap.apply {
            put("userSession", userSession)
            put("username", userSession?.username)
            put("house", userSession?.let { getUserHouse(it.username) })
            put("profilePictureData", getProfilePicture(userSession))
        }
    )

suspend fun ApplicationCall.getProfileTemplate(userSession: UserSession, statusMessage: String? = null) =
    respondTemplate(
        "profile.ftl",
        mapOf(
            "username" to userSession.username,
            "uploadButton" to true,
            "userSession" to userSession,
            "house" to getUserHouse(userSession.username),
            "profilePictureData" to getProfilePicture(userSession),
            "statusMessage" to statusMessage
        )
    )

suspend fun ApplicationCall.getBooksTemplate(userSession: UserSession?) =
   getTemplate(
       "books.ftl",
       userSession,
       mutableMapOf("books" to fetchBooks())
   )

suspend fun ApplicationCall.getCharactersTemplate(userSession: UserSession?) =
    getTemplate(
        "characters.ftl",
        userSession,
        mutableMapOf("characters" to fetchCharacters())
    )

suspend fun ApplicationCall.getHousesTemplate(userSession: UserSession?) =
    getTemplate(
        "houses.ftl",
        userSession,
        mutableMapOf("houses" to fetchHouses())
    )

suspend fun ApplicationCall.getMoviesTemplate(userSession: UserSession?) =
    getTemplate(
        "movies.ftl",
        userSession,
        mutableMapOf("movies" to fetchMovies())
    )

suspend fun ApplicationCall.getPotionsTemplate(userSession: UserSession?) =
    getTemplate(
        "potions.ftl",
        userSession,
        mutableMapOf("potions" to fetchPotions())
    )

suspend fun ApplicationCall.getSpellsTemplate(userSession: UserSession?) =
    getTemplate(
        "spells.ftl",
        userSession,
        mutableMapOf("spells" to fetchSpells())
    )