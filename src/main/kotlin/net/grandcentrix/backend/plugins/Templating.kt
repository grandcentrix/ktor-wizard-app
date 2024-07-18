
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.controllers.getProfilePicture
import net.grandcentrix.backend.controllers.userFavouriteItems
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.plugins.api.APIRequesting
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooks
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchChapters
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchCharacters
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchCharactersPagination
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchMovies
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchPotions
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchPotionsPagination
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchSpells
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchSpellsPagination
import net.grandcentrix.backend.plugins.api.APIRequesting.getBookById
import net.grandcentrix.backend.plugins.api.APIRequesting.getCharacterById
import net.grandcentrix.backend.plugins.api.APIRequesting.getMovieById
import net.grandcentrix.backend.plugins.api.APIRequesting.getPotionById
import net.grandcentrix.backend.plugins.api.APIRequesting.getSpellById

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

suspend fun ApplicationCall.getBookTemplate(
    userSession: UserSession?,
    id: String,
    item: String,
) =
    respondTemplate(
        "book.ftl",
        mapOf(
            "book" to getBookById(id),
            "chapters" to fetchChapters(id),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
        )
    )

suspend fun ApplicationCall.getMovieTemplate(
    userSession: UserSession?,
    id: String,
    item: String,
) =
    respondTemplate(
        "movie.ftl",
        mapOf(
            "movie" to getMovieById(id),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
        )
    )

suspend fun ApplicationCall.getHouseTemplate(
    userSession: UserSession?,
    id: String,
    item: String,
) =
    respondTemplate(
        "house.ftl",
        mapOf(
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "houseDetail" to APIRequesting.getHouseById(id),
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
        )
    )


suspend fun ApplicationCall.getCharacterTemplate(
    userSession: UserSession?,
    id: String,
    item: String,
) =
    respondTemplate(
        "character.ftl",
        mapOf(
            "character" to getCharacterById(id),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
        )
    )

suspend fun ApplicationCall.getSpellTemplate(
    userSession: UserSession?,
    id: String,
    item: String,
) =
    respondTemplate(
        "spell.ftl",
        mapOf(
            "spell" to getSpellById(id),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
        )
    )


suspend fun ApplicationCall.getPotionTemplate(
    userSession: UserSession?,
    id: String,
    item: String,
) =
    respondTemplate(
        "potion.ftl",
        mapOf(
            "potion" to getPotionById(id),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
        )
    )