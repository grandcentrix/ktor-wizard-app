package net.grandcentrix.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import net.grandcentrix.backend.controllers.Login
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.repository.BooksRepository.Companion.BooksRepositoryInstance
import net.grandcentrix.backend.repository.HouseRepository.Companion.HouseRepositoryInstance
import net.grandcentrix.backend.repository.UserRepository.Companion.UserRepositoryInstance

fun Application.configureRouting() {
    routing {
        staticResources("/static", "static")

        route("/") {

            // auxiliary storing if there's a session (user is logged in)
            var userSession: UserSession? = null

//            authenticate("auth-session") {
                get {
//                    call.sessions.set(userSession?.copy())
                    call.respond(FreeMarkerContent(
                        "index.ftl",
                        mapOf(
                            "loginStatus" to LoginInstance.status,
                            "userSession" to userSession.toString()
                        )
                    ))
                LoginInstance.status = ""
            }

            get("/login") {
                call.respond(FreeMarkerContent(
                    "login.ftl",
                    mapOf(
                        "loginStatus" to LoginInstance.status,
                        "userSession" to "null"
                    )
                ))
            }

            authenticate("auth-form") {
                post("/login") {
                    val username = call.principal<UserIdPrincipal>()?.name.toString()
                    call.sessions.set(UserSession(username))
                    userSession = call.sessions.get<UserSession>()
                    LoginInstance.status = "Logged in with success!"
                    call.respondRedirect("/")
                }
            }

            authenticate("auth-session") {
                get("/profile") {
                    val username = userSession?.username
                    call.sessions.set(userSession?.copy())
                    userSession = call.sessions.get<UserSession>()
                    call.respond(FreeMarkerContent(
                        "profile.ftl",
                        mapOf("username" to username, "uploadButton" to true)))
                }
            }

            // TODO("when user is already logged in this page should redirect to home")
            get("/signup") {
                call.respond(FreeMarkerContent(
                    "signup.ftl",
                    mapOf(
                        "signUpStatus" to SignupInstance.status,
                        "userSession" to "null",
                        "houses" to HouseRepositoryInstance.getAll().map { it.name }
                    )
                ))
            }

            post("/signup") {
                val formParameters = call.receiveParameters()
                SignupInstance.createUser(formParameters)
                LoginInstance.status = "Please login with your account"
                call.respondRedirect("/login")
            }

            get("/logout") {
                call.sessions.clear<UserSession>()
                userSession = null
                call.respondRedirect("/login")
            }

            get("/books") {
                call.respondTemplate(
                    "books.ftl",
                    mapOf("books" to BooksRepositoryInstance.getAll())
                )
            }

            get("/houses") {
                call.respondTemplate(
                    "houses.ftl",
                    mapOf("houses" to HouseRepositoryInstance.getAll())
                )
            }

            get("/logout") {
                call.sessions.clear<UserSession>()
                LoginInstance.status = "Logged out with success!"
                call.respondRedirect("/")
            }

            // TODO("try a way to use delete verb instead of post")
            post("/delete-account") {
                // Retrieve the user session
                val userSession = call.sessions.get<UserSession>()

                // Check if the user session is not null
                if (userSession != null) {
                    // Logic to delete the user's account
                    UserRepositoryInstance.deleteItem(userSession.username)
                }
                call.respondRedirect("/logout")
            }
        }
    }
}


