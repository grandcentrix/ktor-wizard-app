package net.grandcentrix.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.GravatarProfile
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchGravatarProfile
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

            // auxiliary storing if there's a session (user is logged in)
            var userSession: UserSession? = null
            var gravatarProfile: GravatarProfile? = null

//            authenticate("auth-session") {
                get {
//                    call.sessions.set(userSession?.copy())
                    call.respond(FreeMarkerContent(
                        "index.ftl",
                        mapOf(
                            "userSession" to userSession.toString(),
                            "avatar" to gravatarProfile?.avatarUrl.toString()
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
                    val userEmail = daoUsers.getItem(username)?.email
                    gravatarProfile = fetchGravatarProfile(userEmail!!)
                    call.respondRedirect("/")
                }
            }

            authenticate("auth-session") {
                get("/profile") {
//                    userSession = call.sessions.get<UserSession>()
                    val username = call.sessions.get<UserSession>()?.username
                    call.respond(FreeMarkerContent(
                        "profile.ftl",
                        mapOf(
                            "username" to username,
                            "uploadButton" to true,
                            "userSession" to userSession.toString(),
                            "avatar" to gravatarProfile?.avatarUrl.toString()
                        )
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
                            "houses" to HousesRepositoryInstance.getAll().map { it.name },
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
                call.respondTemplate(
                    "books.ftl",
                    mapOf(
                        "books" to BooksRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "avatar" to gravatarProfile?.avatarUrl.toString()
                    )
                )
            }

            get("/houses") {
                call.respondTemplate(
                    "houses.ftl",
                    mapOf(
                        "houses" to HousesRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "avatar" to gravatarProfile?.avatarUrl.toString()
                    )
                )
            }

            get("/characters") {
                call.respondTemplate(
                    "characters.ftl",
                    mapOf(
                        "characters" to CharactersRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "avatar" to gravatarProfile?.avatarUrl.toString()
                    )
                )
            }

            get("/movies") {
                call.respondTemplate(
                    "movies.ftl",
                    mapOf(
                        "movies" to MoviesRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "avatar" to gravatarProfile?.avatarUrl.toString()
                    )
                )
            }

            get("/potions") {
                call.respondTemplate(
                    "potions.ftl",
                    mapOf(
                        "potions" to PotionsRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "avatar" to gravatarProfile?.avatarUrl.toString()
                    )
                )
            }

            get("/spells") {
                call.respondTemplate(
                    "spells.ftl",
                    mapOf(
                        "spells" to SpellsRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "avatar" to gravatarProfile?.avatarUrl.toString()
                    )
                )
            }

            get("/logout") {
                call.sessions.clear<UserSession>()
                call.respondRedirect("/")
            }

            // TODO("try a way to use delete verb instead of post")
            post("/delete-account") {
                // Retrieve the user session
//                val userSession = call.sessions.get<UserSession>()

                // Check if the user session is not null
                if (userSession != null) {
                    // Delete user from repository
                    daoUsers.deleteItem(userSession!!.username)
                }
                call.respondRedirect("/logout")
            }
        }
    }
}


