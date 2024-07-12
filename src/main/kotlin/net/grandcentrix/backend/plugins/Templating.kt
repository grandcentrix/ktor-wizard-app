
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.controllers.getProfilePicture
import net.grandcentrix.backend.controllers.userFavouriteItems
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.plugins.api.APIRequesting
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBookById
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooks
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchCharacters
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchCharactersPagination
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchMovieById
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchMovies
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchPotions
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchPotionsPagination
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchSpells
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchSpellsPagination

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
}

suspend fun ApplicationCall.getBooksTemplate(
    userSession: UserSession?,
    item: String,
) =
    respondTemplate(
        "books.ftl",
        mapOf(
            "books" to fetchBooks(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
        )
    )

suspend fun ApplicationCall.getBookById(
    userSession: UserSession?,
    id: String,
    item: String,
) {
    val book = fetchBookById(id)
    if (book != null) {
        respondTemplate(
            "book.ftl",
            mapOf(
                "book" to book,
                "session" to userSession.toString(),
                "username" to userSession?.username,
                "house" to userSession?.let { daoUsers.getHouse(it.username) },
                "profilePictureData" to getProfilePicture(userSession),
                "userFavourites" to userFavouriteItems(userSession?.username, item),
            )
        )
    } else {
        respond(HttpStatusCode.NotFound, "Book not found")
    }
}

suspend fun ApplicationCall.getMovieById(
    userSession: UserSession?,
    id: String,
    item: String,
) {
    val movie = fetchMovieById(id)
    if (movie != null) {
        respondTemplate(
            "movie.ftl",
            mapOf(
                "movie" to movie,
                "session" to userSession.toString(),
                "username" to userSession?.username,
                "house" to userSession?.let { daoUsers.getHouse(it.username) },
                "profilePictureData" to getProfilePicture(userSession),
                "userFavourites" to userFavouriteItems(userSession?.username, item),
            )
        )
    } else {
        respond(HttpStatusCode.NotFound, "Book not found")
    }
}

suspend fun ApplicationCall.getCharactersTemplate(
    userSession: UserSession?,
    item: String,
    pageNumber: String? = null
) =
    respondTemplate(
        "characters.ftl",
        mapOf(
            "characters" to pageNumber?.let { fetchCharacters(it) },
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
            "pagination" to pageNumber?.let { fetchCharactersPagination(it) }
        )
    )

suspend fun ApplicationCall.getHousesTemplate(userSession: UserSession?, item: String) =
    respondTemplate(
        "houses.ftl",
        mapOf(
            "houses" to fetchHouses(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item)
        )
    )

suspend fun ApplicationCall.getMoviesTemplate(userSession: UserSession?, item: String) =
    respondTemplate(
        "movies.ftl",
        mapOf(
            "movies" to fetchMovies(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item)
        )
    )

suspend fun ApplicationCall.getPotionsTemplate(
    userSession: UserSession?,
    item: String,
    pageNumber: String? = null
) =
    respondTemplate(
        "potions.ftl",
        mapOf(
            "potions" to pageNumber?.let { fetchPotions(it) },
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
            "pagination" to pageNumber?.let { fetchPotionsPagination(it) }
        )
    )

suspend fun ApplicationCall.getSpellsTemplate(
    userSession: UserSession?,
    item: String,
    pageNumber: String? = null
) =
    respondTemplate(
        "spells.ftl",
        mapOf(
            "spells" to pageNumber?.let { fetchSpells(it) },
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
            "pagination" to pageNumber?.let { fetchSpellsPagination(it) }
        )
    )