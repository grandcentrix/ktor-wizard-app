package net.grandcentrix.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureStatusPage() {
    routing {
        staticResources("/static", "static")
    }

    install(StatusPages) {
        exception<Exception> { call, cause ->
            when (cause) {
                is AuthorizationException -> call.respondTemplate(
                    "error.ftl",
                    mapOf("errorMessage" to "Error 403. Forbidden.")
                )

                is UnauthorizedException -> {
                    call.respondRedirect("/login")
                }

                is UserAlreadyExistsException -> {
                    call.respondTemplate(
                        "error.ftl",
                        mapOf("errorMessage" to "Error: ${cause.message}")
                    )
                }

                is UserAlreadyExistsException -> { // Handle UserAlreadyExistsException
                    call.respondTemplate(
                        "error.ftl",
                        mapOf("errorMessage" to "Error: ${cause.message}")
                    )
                }


                else -> call.respondTemplate(
                    "error.ftl",
                    mapOf("errorMessage" to "500: Server error - $cause")
                )
            }
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondTemplate(
                "error.ftl",
                mapOf("errorMessage" to "Oops! It wasn't possible to find the page, or it doesn't exist."))
        }
    }
}

//implements a custom exception class
class AuthorizationException(override val message: String?): Exception()
class UserAlreadyExistsException(message: String?) : Exception(message)