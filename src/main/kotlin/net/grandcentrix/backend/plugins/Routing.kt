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
import net.grandcentrix.backend.repository.BooksManager.Companion.BooksManagerInstance
import net.grandcentrix.backend.repository.HouseManager.Companion.HouseManagerInstance

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")

        route("/") {

            get {
                call.respond(FreeMarkerContent(
                    "index.ftl",
                    mapOf("loginStatus" to LoginInstance.status)
                ))
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

            // TODO("logout route")

            authenticate("auth-session") {
                get("/profile") {
                    val userSession = call.sessions.get<UserSession>()
                    val username = userSession?.username
                    call.sessions.set(userSession?.copy())
                    call.respond(FreeMarkerContent("profile.ftl", mapOf("username" to username, "uploadButton" to true)))
                }
            }

            // TODO("when user is already logged in this page should redirect to home")
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

            get("/books") {
                call.respondTemplate(
                    "books.ftl",
                    mapOf("books" to BooksManagerInstance.getAll())
                )
            }
        }
    }
}

