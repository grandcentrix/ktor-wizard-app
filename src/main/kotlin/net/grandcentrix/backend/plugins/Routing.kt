package net.grandcentrix.backend.plugins

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import net.grandcentrix.backend.controllers.*
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses

fun Application.configureRouting() {

    routing {
        staticResources("/static", "static")

        route("/") {

            get {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                call.respond(
                    FreeMarkerContent(
                        "index.ftl",
                        mapOf(
                            "userSession" to userSession,
                            "house" to userSession?.let { getUserHouse(it.username) },
                            "profilePictureData" to getProfilePicture(userSession)
                        )
                    )
                )
            }

            get("/login") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                call.respond(
                    FreeMarkerContent(
                        "login.ftl",
                        mapOf(
                            "userSession" to userSession,
                            "profilePictureData" to getProfilePicture(userSession)
                        )
                    )
                )
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
                        ?: throw UnauthorizedException("User session not found")
                    call.getProfileTemplate(userSession)
                }
            }

            get("/signup") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                if (userSession != null) {
                    call.respondRedirect("/")
                } else {
                    call.respond(
                        FreeMarkerContent(
                            "signup.ftl",
                            mapOf(
                                "userSession" to "null",
                                "houses" to fetchHouses(),
                                "profilePictureData" to getProfilePicture(userSession=null),
                                "statusMessage" to null
                            )
                        )
                    )
                }
            }

            post("/signup") {
                val formParameters = call.receiveParameters()
                SignupInstance.createUser(formParameters)
                call.respondRedirect("/login")
            }

            get("/logout") {
                call.sessions.clear<UserSession>()
                call.respondRedirect("/login")
            }

            get("/books") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                call.getBooksTemplate(userSession)
            }

            get("/houses") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                call.getHousesTemplate(userSession)
            }

            get("/characters") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                call.getCharactersTemplate(userSession)
            }

            get("/movies") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                call.getMoviesTemplate(userSession)
            }

            get("/potions") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                call.getPotionsTemplate(userSession)
            }

            get("/spells") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                call.getSpellsTemplate(userSession)
            }

            get("/logout") {
                call.sessions.clear<UserSession>()
                call.respondRedirect("/")
            }

            delete("/user/account") {
                val userSession = call.verifyUserSession()
                if (userSession != null) {
                    call.deleteAccount(userSession)
                }
            }

            authenticate("auth-session") {
                put("/user/username") {
                    val userSession = call.verifyUserSession()
                    val parameters = call.receiveParameters()
                    val newUsername = parameters["newUsername"]
                    if (userSession != null) {
                        call.updateUsername(userSession, newUsername)
                    }
                }
            }

            put("/user/email") {
                val userSession = call.verifyUserSession()
                val parameters = call.receiveParameters()
                val newEmail = parameters["newEmail"]
                if (userSession != null) {
                    call.updateEmail(userSession, newEmail)
                }
            }

            put("/user/password") {
                val userSession = call.verifyUserSession()
                val parameters = call.receiveParameters()
                val newPassword = parameters["newPassword"]
                if (userSession != null) {
                    call.updatePassword(userSession, newPassword)
                }
            }

            post("/user/profilepicture") {
                val userSession = call.verifyUserSession()
                val multipartData = call.receiveMultipart()
                val imageDataPart = multipartData.readPart() as? PartData.FileItem
                val imageData = imageDataPart?.streamProvider?.invoke()?.readBytes()
                if (userSession != null) {
                    call.updateProfilePicture(userSession, imageData)
                }
            }

            delete("/user/profilePicture") {
                val userSession = call.verifyUserSession()
                if (userSession != null) {
                    call.removeProfilePicture(userSession)
                }
            }

            get("/hogwarts-house") {
                val userSession = call.verifyUserSession()
                if (userSession != null) {
                    call.getHogwartsHouse(userSession)
                }
            }
        }
    }
}