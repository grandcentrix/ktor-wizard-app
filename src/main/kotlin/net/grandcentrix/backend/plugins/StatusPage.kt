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
import net.grandcentrix.backend.dao.daoUsers
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

                is DAOException -> {
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
                    val userSession = call.sessions.get<UserSession>()
                        ?: throw UnauthorizedException("User session not found")
                    call.getProfileTemplate(userSession, statusMessage = cause.message)
                }

                is SignupException -> {
                    call.respondTemplate(
                        "signup.ftl",
                        mapOf(
                            "userSession" to "null",
                            "houses" to fetchHouses(),
                            "profilePictureData" to getProfilePicture(userSession=null),
                            "statusMessage" to cause.message
                        )
                    )
                }

                is GravatarProfileException -> {
                    call.request.local.uri
                }

                is ProfilePictureException -> {
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    call.respondTemplate("profile.ftl",
                        mapOf(
                            "username" to userSession?.username,
                            "uploadButton" to true,
                            "userSession" to userSession,
                            "house" to userSession?.let { daoUsers.getHouse(it.username) },
                            "profilePictureData" to getProfilePicture(userSession),
                            "pictureStatusMessage" to cause.message
                        )
                    )
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
class DAOException(override val message: String?): StatusException(message)
class RequestException(override val message: String?): StatusException(message)
class UserAlreadyExistsException(override val message: String?): StatusException(message)
class SignupException(override val message: String?): StatusException(message)
class UnauthorizedException(override val message: String?): StatusException(message)
class GravatarProfileException(
    override val message: String?,
    override val cause: Throwable? = null
): StatusException(message)
class ProfilePictureException(override val message: String?): StatusException(message)