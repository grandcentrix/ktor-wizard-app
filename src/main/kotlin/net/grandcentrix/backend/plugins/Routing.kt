package net.grandcentrix.backend.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import net.grandcentrix.backend.controllers.GravatarProfile
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.controllers.UserSession
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
                val gravatarProfile = GravatarProfile().getGravatarProfile(userSession)
                call.respond(FreeMarkerContent(
                    "index.ftl",
                    mapOf(
                        "session" to userSession.toString(),
                        "avatar" to gravatarProfile?.avatarUrl.toString()
                    )
                ))
            }

            get("/login") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                call.respond(FreeMarkerContent(
                    "login.ftl",
                    mapOf(
                        "userSession" to userSession.toString()
                    )
                ))
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
                    val gravatarProfile = GravatarProfile().getGravatarProfile(userSession)
                    call.respond(FreeMarkerContent(
                        "profile.ftl",
                        mapOf(
                            "username" to userSession?.username.toString(),
                            "uploadButton" to true,
                            "userSession" to userSession.toString(),
                            "avatar" to gravatarProfile?.avatarUrl.toString()
                        )
                    ))
                }
            }

            get("/signup") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
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
                call.respondRedirect("/login")
            }

            get("/books") {
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val gravatarProfile = GravatarProfile().getGravatarProfile(userSession)
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
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val gravatarProfile = GravatarProfile().getGravatarProfile(userSession)
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
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val gravatarProfile = GravatarProfile().getGravatarProfile(userSession)
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
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val gravatarProfile = GravatarProfile().getGravatarProfile(userSession)
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
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val gravatarProfile = GravatarProfile().getGravatarProfile(userSession)
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
                val userSession: UserSession? = call.sessions.get<UserSession>()
                val gravatarProfile = GravatarProfile().getGravatarProfile(userSession)
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
                val userSession: UserSession? = call.sessions.get<UserSession>()

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


