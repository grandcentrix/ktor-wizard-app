import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.controllers.getProfilePicture
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.repository.*

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
            "books" to BooksRepository.BooksRepositoryInstance.getAll(),
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
            "characters" to CharactersRepository.CharactersRepositoryInstance.getAll(),
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
            "houses" to HousesRepository.HousesRepositoryInstance.getAll(),
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
            "movies" to MoviesRepository.MoviesRepositoryInstance.getAll(),
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
            "potions" to PotionsRepository.PotionsRepositoryInstance.getAll(),
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
            "spells" to SpellsRepository.SpellsRepositoryInstance.getAll(),
            "session" to userSession.toString(),
            "username" to userSession?.username,
            "house" to userSession?.let { daoUsers.getHouse(it.username) },
            "profilePictureData" to getProfilePicture(userSession),
        )
    )