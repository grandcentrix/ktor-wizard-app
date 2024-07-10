
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.controllers.getProfilePicture
import net.grandcentrix.backend.controllers.userFavouriteItems
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooks
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchBooksPagination
import net.grandcentrix.backend.repository.*

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
}

suspend fun ApplicationCall.getBooksTemplate(
    userSession: UserSession?,
    item: String,
    pageNumber: String? = null
) =
    respondTemplate(
        "books.ftl",
        mapOf(
            "books" to pageNumber?.let { fetchBooks(it) },
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item),
            "pagination" to pageNumber?.let { fetchBooksPagination(it) }
        )
    )

suspend fun ApplicationCall.getCharactersTemplate(userSession: UserSession?, item: String) =
    respondTemplate(
        "characters.ftl",
        mapOf(
            "characters" to CharactersRepository.CharactersRepositoryInstance.getAll(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item)
        )
    )

suspend fun ApplicationCall.getHousesTemplate(userSession: UserSession?, item: String) =
    respondTemplate(
        "houses.ftl",
        mapOf(
            "houses" to HousesRepository.HousesRepositoryInstance.getAll(),
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
            "movies" to MoviesRepository.MoviesRepositoryInstance.getAll(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item)
        )
    )

suspend fun ApplicationCall.getPotionsTemplate(userSession: UserSession?, item: String) =
    respondTemplate(
        "potions.ftl",
        mapOf(
            "potions" to PotionsRepository.PotionsRepositoryInstance.getAll(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item)
        )
    )

suspend fun ApplicationCall.getSpellsTemplate(userSession: UserSession?, item: String) =
    respondTemplate(
        "spells.ftl",
        mapOf(
            "spells" to SpellsRepository.SpellsRepositoryInstance.getAll(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
            "userFavourites" to userFavouriteItems(userSession?.username, item)
        )
    )