package net.grandcentrix.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.repository.HouseManager.Companion.HouseManagerInstance
import net.grandcentrix.backend.repository.UserManager.Companion.UserManagerInstance

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")

        route("/") {

            authenticate("auth-session") {
                get {
                    val userSession = call.principal<UserSession>()
                    call.sessions.set(userSession?.copy())
                    call.respond(FreeMarkerContent(
                        "index.ftl",
                        mapOf("loginStatus" to LoginInstance.status)
                    ))
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

            authenticate("auth-session") {
                get("/profile") {
                    val userSession = call.sessions.get<UserSession>()
                    val username = userSession?.username
                    call.sessions.set(userSession?.copy())
                    call.respond(FreeMarkerContent("profile.ftl", mapOf("username" to username, "uploadButton" to true)))
                }
            }

            get("/signup") {
                call.respond(FreeMarkerContent(
                    "signup.ftl",
                    mapOf(
                        "signUpStatus" to SignupInstance.status,
                        "houses" to HouseManagerInstance.getAll().map { it.name }
                    )
                ))
            }

            post("/signup") {
                val formParameters = call.receiveParameters()
                SignupInstance.createUser(formParameters)
                LoginInstance.status = "Please login with your account"
                call.respondRedirect("/login")
            }
        }
        post("/delete-account") {
            // Retrieve the user session
            val userSession = call.sessions.get<UserSession>()

            // Check if the user session is not null
            if (userSession != null) {
                // Logic to delete the user's account
                // For example:
                UserManagerInstance.deleteItem(userSession.username)
            }

            // Clear the session
            call.sessions.clear<UserSession>()
            call.respondRedirect("/login")
        }
    }
}


