package net.grandcentrix.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.controllers.UserSession


fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")

        route("/") {

            authenticate("auth-session") {
                get {
                    val userSession = call.principal<UserSession>()
                    call.sessions.set(userSession?.copy())
                    call.respond("${LoginInstance.status} Hello, world!")
                }
            }

            get("/login") {
                call.respond(FreeMarkerContent("login.ftl", mapOf("loginStatus" to LoginInstance.status)))
            }

            authenticate("auth-form") {
                post("/login") {
                    val username = call.principal<UserIdPrincipal>()?.name.toString()
                    call.sessions.set(UserSession(username))
                    call.respondRedirect("/")
                }
            }
        }
    }
}
