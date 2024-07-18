package net.grandcentrix.backend.plugins

import getBookTemplate
import getBooksTemplate
import getCharacterTemplate
import getCharactersTemplate
import getHouseTemplate
import getHousesTemplate
import getMovieTemplate
import getMoviesTemplate
import getPotionTemplate
import getPotionsTemplate
import getSpellTemplate
import getSpellsTemplate
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import net.grandcentrix.backend.controllers.*
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchHouses

fun Application.configureRouting() {

    routing {
        staticResources("/static", "static")

        route("/") {

            get {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val username = call.sessions.get<UserSession>()?.username
                call.respond(
                    FreeMarkerContent(
                        "index.ftl",
                        mapOf(
                            "session" to userSession.toString(),
                            "username" to username,
                            "house" to userSession?.let { daoUsers.getHouse(it.username) },
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
                            "session" to userSession.toString(),
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
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    val username = call.sessions.get<UserSession>()?.username
                    call.respond(
                        FreeMarkerContent(
                            "profile.ftl",
                            mapOf(
                                "username" to username,
                                "uploadButton" to true,
                                "session" to userSession.toString(),
                                "house" to userSession?.let { daoUsers.getHouse(it.username) },
                                "profilePictureData" to getProfilePicture(userSession)
                            )
                        )
                    )
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
                                "session" to "null",
                                "houses" to fetchHouses(),
                                "profilePictureData" to getProfilePicture(userSession=null),
                                "message" to ""
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
                val item = call.request.local.uri.removePrefix("/")
                call.getBooksTemplate(userSession, item)
            }

            get("/books/{id}") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val item = call.request.origin.uri
                val id = call.parameters["id"]!!
                call.getBookTemplate(userSession, id, item)
            }


            get("/characters/{id}") {
                val id = call.parameters["id"]!!
                if (id.toIntOrNull() in 1..500) {
                    val pageNumber = id
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    val item = call.request.local.uri.removePrefix("/")
                    call.getCharactersTemplate(userSession, item, pageNumber)
                } else {
                    val characterId = id
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    val item = call.request.local.uri.removePrefix("/")
                    call.getCharacterTemplate(userSession, characterId, item)
                }
            }

            get("/spells/{id}") {
                val id = call.parameters["id"]!!
                if (id.toIntOrNull() in 1..500) {
                    val pageNumber = id
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    val item = call.request.local.uri.removePrefix("/")
                    call.getSpellsTemplate(userSession, item, pageNumber)
                } else {
                    val spellid = id
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    val item = call.request.local.uri.removePrefix("/")
                    call.getSpellTemplate(userSession, spellid, item)
                }
            }

            get("/potions/{id}") {
                val id = call.parameters["id"]!!
                if (id.toIntOrNull() in 1..500) {
                    val pageNumber = id
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    val item = call.request.local.uri.removePrefix("/")
                    call.getPotionsTemplate(userSession, item, pageNumber)
                } else {
                    val potionid = id
                    val userSession: UserSession? = call.sessions.get<UserSession>()
                    val item = call.request.local.uri.removePrefix("/")
                    call.getPotionTemplate(userSession, potionid, item)
                }
            }

            get("/movies/{id}") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val item = call.request.local.uri.removePrefix("/")
                val id = call.parameters["id"]!!
                call.getMovieTemplate(userSession, id, item)
            }

            get("/houses/{id}") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val item = call.request.local.uri.removePrefix("/")
                val id = call.parameters["id"]!!
                call.getHouseTemplate(userSession, id, item)
            }

            get("/houses") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val item = call.request.local.uri.removePrefix("/")
                call.getHousesTemplate(userSession, item)
            }

            get("/characters") {
                call.respondRedirect("/characters/1")
            }

            get("/characters/{pageNumber}") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val pageNumber = call.parameters.getOrFail<String>("pageNumber")
                val item = call.request.local.uri.removePrefix("/")
                call.getCharactersTemplate(userSession, item, pageNumber)
            }

            get("/movies") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val item = call.request.local.uri.removePrefix("/")
                call.getMoviesTemplate(userSession, item)
            }

            get("/potions") {
                call.respondRedirect("/potions/1")
            }

            get("/potions/{pageNumber}") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val pageNumber = call.parameters.getOrFail<String>("pageNumber")
                val item = call.request.local.uri.removePrefix("/")
                call.getPotionsTemplate(userSession, item, pageNumber)
            }

            get("/spells") {
                call.respondRedirect("/spells/1")
            }

            get("/spells/{pageNumber}") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val pageNumber = call.parameters.getOrFail<String>("pageNumber")
                val item = call.request.local.uri.removePrefix("/")
                call.getSpellsTemplate(userSession, item, pageNumber)
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

            put("/user/profilepicture") {
                val userSession = call.verifyUserSession()
                val multipartData = call.receiveMultipart()
                val imageDataPart = multipartData.readPart() as? PartData.FileItem
                val imageData = imageDataPart?.streamProvider?.invoke()?.readBytes()
                if (userSession != null) {
                    call.updateProfilePicture(userSession, imageData)
                }
            }

            delete("/user/profilepicture") {
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

            authenticate("auth-session") {
                post("/{item}/{itemId}/favourite") {
                    val userSession = call.sessions.get<UserSession>()
                        ?: throw UnauthorizedException("User session not found.")
                    call.addFavouriteItem(userSession)
                }
            }

            authenticate("auth-session") {
                delete("/{item}/{itemId}/favourite") {
                    val userSession = call.sessions.get<UserSession>()
                        ?: throw UnauthorizedException("User session not found.")
                    call.removeFavouriteItem(userSession)
                }
            }
    }
}
}