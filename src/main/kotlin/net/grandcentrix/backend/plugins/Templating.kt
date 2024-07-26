package net.grandcentrix.backend.plugins

import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.controllers.getProfilePicture
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooks
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchCharacters
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchMovies
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchPotions
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchSpells

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
}

suspend fun ApplicationCall.getProfileTemplate(userSession: UserSession?, statusMessage: String? = null) =
    respondTemplate(
        "profile.ftl",
        mapOf(
            "username" to userSession?.username,
            "uploadButton" to true,
            "session" to userSession.toString(),
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "statusMessage" to statusMessage
        )
    )

suspend fun ApplicationCall.getBooksTemplate(userSession: UserSession?) =
    respondTemplate(
        "books.ftl",
        mapOf(
            "books" to fetchBooks(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
        )
    )

suspend fun ApplicationCall.getCharactersTemplate(userSession: UserSession?) =
    respondTemplate(
        "characters.ftl",
        mapOf(
            "characters" to fetchCharacters(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
        )
    )

suspend fun ApplicationCall.getHousesTemplate(userSession: UserSession?) =
    respondTemplate(
        "houses.ftl",
        mapOf(
            "houses" to fetchHouses(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
        )
    )

suspend fun ApplicationCall.getMoviesTemplate(userSession: UserSession?) =
    respondTemplate(
        "movies.ftl",
        mapOf(
            "movies" to fetchMovies(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
        )
    )

suspend fun ApplicationCall.getPotionsTemplate(userSession: UserSession?) =
    respondTemplate(
        "potions.ftl",
        mapOf(
            "potions" to fetchPotions(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
        )
    )

suspend fun ApplicationCall.getSpellsTemplate(userSession: UserSession?) =
    respondTemplate(
        "spells.ftl",
        mapOf(
            "spells" to fetchSpells(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
        )
    )