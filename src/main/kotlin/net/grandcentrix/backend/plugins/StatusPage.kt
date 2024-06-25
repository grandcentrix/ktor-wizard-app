package net.grandcentrix.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.grandcentrix.backend.repository.HousesRepository

fun Application.configureStatusPage() {
    routing {
        staticResources("/static", "static")
    }

    install(StatusPages) {
        exception<StatusException> { call, cause ->
            when (cause) {
                is RequestException -> {
                    call.respondTemplate(
                        "error.ftl",
                        mapOf(
                            "errorMessage" to cause.message,
                            "redirectLink" to call.request.local.uri
                        )
                    )
                }

                is UnauthorizedException -> call.respondRedirect( "/login")

                is DAOException -> {
                    call.respondTemplate(
                        "error.ftl",
                        mapOf(
                            "errorMessage" to cause.message,
                            "redirectLink" to call.request.local.uri
                        )
                    )
                }

                is UserAlreadyExistsException -> {
                    call.respondTemplate(
                        "error.ftl",
                        mapOf(
                            "errorMessage" to cause.message,
                            "redirectLink" to "/signup"
                        )
                    )
                }

                is SignupInvalidInput -> {
                    call.respondTemplate(
                        "signup.ftl",
                        mapOf(
                            "userSession" to "null",
                            "houses" to HousesRepository.HousesRepositoryInstance.getAll().map { it.name },
                            "message" to cause.message
                        )
                    )
                }

                else ->
                    call.respondTemplate(
                        "error.ftl",
                        mapOf(
                            "errorMessage" to cause.message,
                            "redirectLink" to call.request.local.uri
                        )
                    )
            }
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondTemplate(
                "error.ftl",
                mapOf(
                    "errorMessage" to "Oops! It wasn't possible to find the page, or it doesn't exist.",
                    "redirectLink" to "/"
                ))
        }
    }
}


open class StatusException(override val message: String?): Exception()

class DAOException(override val message: String?): StatusException(message)
class RequestException(override val message: String?): StatusException(message)
class UserAlreadyExistsException(override val message: String?): StatusException(message)
class SignupInvalidInput(override val message: String?): StatusException(message)
class UnauthorizedException(override val message: String?): StatusException(message)
