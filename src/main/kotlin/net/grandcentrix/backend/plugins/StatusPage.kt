package net.grandcentrix.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.controllers.getProfilePicture
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses

fun Application.configureStatusPage() {
    routing {
        staticResources("/static", "static")
    }

    install(StatusPages) {
        exception<StatusException> { call, cause ->

            when (cause) {
                is RequestException -> {
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    call.respondTemplate(
                        "error.ftl",
                        mapOf(
                            "errorMessage" to cause.message,
                            "redirectLink" to call.request.local.uri,
                            "session" to userSession.toString(),
                            "profilePictureData" to getProfilePicture(userSession)
                        )
                    )
                }

                is UnauthorizedException -> call.respondRedirect( "/login")

                is DAOUsersException -> {
                    val userSession = call.sessions.get<UserSession>()
                    call.respondTemplate(
                        "error.ftl",
                        mapOf(
                            "errorMessage" to cause.message,
                            "redirectLink" to call.request.local.uri,
                            "session" to userSession.toString(),
                            "profilePicture" to getProfilePicture(userSession)
                        )
                    )
                }

                is UserAlreadyExistsException -> {
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    call.respondTemplate(
                        "error.ftl",
                        mapOf(
                            "errorMessage" to cause.message,
                            "redirectLink" to "/signup",
                            "session" to userSession.toString(),
                            "profilePictureData" to getProfilePicture(userSession)
                        )
                    )
                }

                is SignupException -> {
                    call.respondTemplate(
                        "signup.ftl",
                        mapOf(
                            "userSession" to "null",
                            "houses" to fetchHouses(),
                            "profilePictureData" to getProfilePicture(userSession=null),
                            "message" to cause.message
                        )
                    )
                }

                is GravatarProfileException -> {
                    call.request.local.uri //FIXME
                }

                is FavouriteItemsException -> {
                    val message = cause.message ?: "Could not complete the action."
                    //TODO("log the message")
                    call.request.local.uri
                }

                is DAOFavouriteItemsException -> {
                    val message = cause.message ?: "Database error: Could not complete the action."
                    // TODO("log the message)
                    call.request.local.uri
                }

                else -> {
                    val userSession = call.sessions.get<UserSession>()
                    call.respondTemplate(
                        "error.ftl",
                        mapOf(
                            "errorMessage" to cause.message,
                            "redirectLink" to call.request.local.uri,
                            "session" to userSession.toString(),
                            "profilePictureData" to getProfilePicture(userSession)
                        )
                    )
                }
            }
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            val userSession: UserSession? = call.sessions.get<UserSession>()
            call.respondTemplate(
                "error.ftl",
                mapOf(
                    "errorMessage" to "Oops! It wasn't possible to find the page, or it doesn't exist.",
                    "redirectLink" to "/",
                    "session" to userSession.toString(),
                    "profilePictureData" to getProfilePicture(userSession)
                )
            )
        }

        status(HttpStatusCode.InternalServerError) { call, _ ->
            val userSession: UserSession? = call.sessions.get<UserSession>()
            call.respondTemplate(
                "error.ftl",
                mapOf(
                    "errorMessage" to "Status 500 - Internal Server Error",
                    "redirectLink" to "/",
                    "session" to userSession.toString(),
                    "profilePictureData" to getProfilePicture(userSession)
                ))
        }
    }
}


open class StatusException(override val message: String?): Exception()

class DAOUsersException(override val message: String?): StatusException(message)
class DAOFavouriteItemsException(override val message: String?): StatusException(message)
class RequestException(override val message: String?): StatusException(message)
class UserAlreadyExistsException(override val message: String?): StatusException(message)
class SignupException(override val message: String?): StatusException(message)
class UnauthorizedException(override val message: String?): StatusException(message)
class GravatarProfileException(
    override val message: String?,
    override val cause: Throwable? = null
): StatusException(message)

class FavouriteItemsException(override val message: String?): StatusException(message)

fun Unit.addErrorMessage(errorMessage: String): String {
    return errorMessage
}