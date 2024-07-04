package net.grandcentrix.backend.plugins

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import net.grandcentrix.backend.controllers.*
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.repository.BooksRepository.Companion.BooksRepositoryInstance
import net.grandcentrix.backend.repository.CharactersRepository.Companion.CharactersRepositoryInstance
import net.grandcentrix.backend.repository.HousesRepository.Companion.HousesRepositoryInstance
import net.grandcentrix.backend.repository.MoviesRepository.Companion.MoviesRepositoryInstance
import net.grandcentrix.backend.repository.PotionsRepository.Companion.PotionsRepositoryInstance
import net.grandcentrix.backend.repository.SpellsRepository.Companion.SpellsRepositoryInstance

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
                                "houses" to HousesRepositoryInstance.getAll().map { mapOf("id" to it.id, "name" to it.name) },
                                "profilePictureData" to getProfilePicture(userSession)
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
                val username = call.sessions.get<UserSession>()?.username
                val item = call.request.local.uri.removePrefix("/")
                call.respondTemplate(
                    "books.ftl",
                    mapOf(
                        "books" to BooksRepositoryInstance.getAll(),
                        "session" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { daoUsers.getHouse(it.username) },
                        "profilePictureData" to getProfilePicture(userSession),
                        "userFavourites" to userFavouriteItems(username, item)
                    )
                )
            }

            get("/houses") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val username = call.sessions.get<UserSession>()?.username
                call.respondTemplate(
                    "houses.ftl",
                    mapOf(
                        "houses" to HousesRepositoryInstance.getAll(),
                        "session" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { daoUsers.getHouse(it.username) },
                        "profilePictureData" to getProfilePicture(userSession)
                    )
                )
            }

            get("/characters") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val username = call.sessions.get<UserSession>()?.username
                call.respondTemplate(
                    "characters.ftl",
                    mapOf(
                        "characters" to CharactersRepositoryInstance.getAll(),
                        "session" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { daoUsers.getHouse(it.username) },
                        "profilePictureData" to getProfilePicture(userSession)
                    )
                )
            }

            get("/movies") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val username = call.sessions.get<UserSession>()?.username

                call.respondTemplate(
                    "movies.ftl",
                    mapOf(
                        "movies" to MoviesRepositoryInstance.getAll(),
                        "session" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { daoUsers.getHouse(it.username) },
                        "profilePictureData" to getProfilePicture(userSession)
                    )
                )
            }

            get("/potions") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val username = call.sessions.get<UserSession>()?.username

                call.respondTemplate(
                    "potions.ftl",
                    mapOf(
                        "potions" to PotionsRepositoryInstance.getAll(),
                        "session" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { daoUsers.getHouse(it.username) },
                        "profilePictureData" to getProfilePicture(userSession)
                    )
                )
            }

            get("/spells") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val username = call.sessions.get<UserSession>()?.username

                call.respondTemplate(
                    "spells.ftl",
                    mapOf(
                        "spells" to SpellsRepositoryInstance.getAll(),
                        "session" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { daoUsers.getHouse(it.username) },
                        "profilePictureData" to getProfilePicture(userSession)
                    )
                )
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

            get("/hogwards-house") {
                val userSession = call.verifyUserSession()
                if (userSession != null) {
                    call.getHogwartsHouse(userSession)
                }
            }

            authenticate("auth-session") {
                post("/{item}/{itemId}/favourite") {
                    call.addFavouriteItem()
                }
            }

            authenticate("auth-session") {
                delete("/{item}/{itemId}/favourite") {
                    val item = call.parameters.getOrFail<String>("item")
                    val itemId = call.parameters.getOrFail<String>("itemId")
                    val username = call.sessions.get<UserSession>()?.username
                        ?: throw UnauthorizedException("Username for session not found.")
//                    removeItem(item, itemId, username)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}