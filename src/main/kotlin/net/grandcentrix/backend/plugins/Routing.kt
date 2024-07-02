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
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.controllers.addFavouriteItem
import net.grandcentrix.backend.controllers.userFavouriteItems
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.Users.username
import net.grandcentrix.backend.repository.BooksRepository.Companion.BooksRepositoryInstance
import net.grandcentrix.backend.repository.CharactersRepository.Companion.CharactersRepositoryInstance
import net.grandcentrix.backend.repository.HousesRepository.Companion.HousesRepositoryInstance
import net.grandcentrix.backend.repository.MoviesRepository.Companion.MoviesRepositoryInstance
import net.grandcentrix.backend.repository.PotionsRepository.Companion.PotionsRepositoryInstance
import net.grandcentrix.backend.repository.SpellsRepository.Companion.SpellsRepositoryInstance

@OptIn(ExperimentalStdlibApi::class)
fun Application.configureRouting() {

    routing {
        staticResources("/static", "static")

        route("/") {

            // auxiliary storing if there's a session (user is logged in)
            var userSession: UserSession? = null

            get {
                val username = call.sessions.get<UserSession>()?.username
                call.respond(FreeMarkerContent(
                    "index.ftl",
                    mapOf(
                        "userSession" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { it1 -> daoUsers.getHouse(it1.username) }

                    )
                ))
            }

            get("/login") {
                call.respond(FreeMarkerContent(
                    "login.ftl",
                    mapOf(
                        "userSession" to "null"
                    )
                ))
            }

            authenticate("auth-form") {
                post("/login") {
                    val username = call.principal<UserIdPrincipal>()?.name.toString()
                    call.sessions.set(UserSession(username))
                    userSession = call.sessions.get<UserSession>()
                    call.respondRedirect("/")
                }
            }

            authenticate("auth-session") {
                get("/profile") {
                    val username = call.sessions.get<UserSession>()?.username
                    call.respond(FreeMarkerContent(
                        "profile.ftl",
                        mapOf("username" to username, "uploadButton" to true, "userSession" to userSession.toString(),"house" to userSession?.let { it1 ->
                            daoUsers.getHouse(
                                it1.username)
                        })
                    ))
                }
            }

            get("/signup") {
                if (userSession != null) {
                    call.respondRedirect("/")
                } else {
                    call.respond(FreeMarkerContent(
                        "signup.ftl",
                        mapOf(
                            "userSession" to "null",
                            "houses" to HousesRepositoryInstance.getAll()
                        )
                    ))
                }
            }

            post("/signup") {
                val formParameters = call.receiveParameters()
                SignupInstance.createUser(formParameters)
                call.respondRedirect("/login")
            }

            get("/logout") {
                call.sessions.clear<UserSession>()
                userSession = null
                call.respondRedirect("/login")
            }

            get("/books") {
                val username = call.sessions.get<UserSession>()?.username
                val item = call.request.local.uri.removePrefix("/")
                call.respondTemplate(
                    "books.ftl",
                    mapOf(
                        "books" to BooksRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { it1 -> daoUsers.getHouse(it1.username) },
                        "userFavourites" to userFavouriteItems(username, item)
                    )
                )
            }

            get("/houses") {
                val username = call.sessions.get<UserSession>()?.username
                call.respondTemplate(
                    "houses.ftl",
                    mapOf(
                        "houses" to HousesRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { it1 -> daoUsers.getHouse(it1.username) }
                    )
                )
            }

            get("/characters") {
                val username = call.sessions.get<UserSession>()?.username
                call.respondTemplate(
                    "characters.ftl",
                    mapOf(
                        "characters" to CharactersRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { it1 -> daoUsers.getHouse(it1.username) }
                    )
                )
            }

            get("/movies") {
                val username = call.sessions.get<UserSession>()?.username
                call.respondTemplate(
                    "movies.ftl",
                    mapOf(
                        "movies" to MoviesRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { it1 -> daoUsers.getHouse(it1.username) }
                    )
                )
            }

            get("/potions") {
                val username = call.sessions.get<UserSession>()?.username
                call.respondTemplate(
                    "potions.ftl",
                    mapOf(
                        "potions" to PotionsRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { it1 -> daoUsers.getHouse(it1.username) }
                    )
                )
            }

            get("/spells") {
                val username = call.sessions.get<UserSession>()?.username
                call.respondTemplate(
                    "spells.ftl",
                    mapOf(
                        "spells" to SpellsRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "username" to username,
                        "house" to userSession?.let { it1 -> daoUsers.getHouse(it1.username) }
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

                get("/profile-picture") {
                    val userSession = call.verifyUserSession()
                    if (userSession!= null) {
                        if (userSession != null) {
                            call.getProfilePicture(userSession)
                        call.respond(FreeMarkerContent(
                            "_layout.ftl",
                            mapOf("userSession" to userSession, "username" to username, "house" to userSession?.let { it1 -> daoUsers.getHouse(it1.username)
                            })))
                    }
                }
            }

            authenticate("auth-session") {
                post("/{item}/{itemId}/favourite") {
                    val item = call.parameters.getOrFail<String>("item")
                    val itemId = call.parameters.getOrFail<String>("itemId")
                    val username = call.sessions.get<UserSession>()?.username
                        ?: throw UnauthorizedException("Username for session not found.")
                    addFavouriteItem(item, itemId, username)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
    }
}

