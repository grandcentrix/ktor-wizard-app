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
import net.grandcentrix.backend.controllers.Login.Companion.LoginInstance
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.Users.password
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


//            authenticate("auth-session") {
            get {
                val username = call.sessions.get<UserSession>()?.username
//                    call.sessions.set(userSession?.copy())
                call.respond(FreeMarkerContent(
                    "index.ftl",
                    mapOf(
                        "loginStatus" to LoginInstance.status,
                        "userSession" to userSession.toString(),
                        "username" to username
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
//                    userSession = call.sessions.get<UserSession>()
                    val username = call.sessions.get<UserSession>()?.username
                    call.respond(FreeMarkerContent(
                        "profile.ftl",
                        mapOf("username" to username, "uploadButton" to true,"userSession" to userSession.toString())))
                }
            }

            get("/signup") {
                if (userSession != null) {
                    call.respondRedirect("/")
                } else {
                    val currentStatus = SignupInstance.status
                    SignupInstance.status = "" // Reset the status after reading it
                    call.respond(FreeMarkerContent(
                        "signup.ftl",
                        mapOf(
                            "signUpStatus" to currentStatus,
                            "userSession" to "null",
                            "houses" to HousesRepositoryInstance.getAll().map { it.name }
                        )
                    ))
                }
            }

            post("/signup") {
                val formParameters = call.receiveParameters()
                SignupInstance.createUser(formParameters)
                val currentStatus = SignupInstance.status
                SignupInstance.status = "" // Reset the status after setting it
                LoginInstance.status = "Please login with your account"
                call.respondRedirect("/login")
            }

            get("/logout") {
                call.sessions.clear<UserSession>()
                userSession = null
                call.respondRedirect("/login")
            }

            get("/books") {
                val username = call.sessions.get<UserSession>()?.username
                call.respondTemplate(
                    "books.ftl",
                    mapOf(
                        "books" to BooksRepositoryInstance.getAll(),
                        "userSession" to userSession.toString(),
                        "username" to username,
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
                        "username" to username
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
                        "username" to username
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
                        "username" to username
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
                        "username" to username
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
                        "username" to username
                    )
                )
            }

            get("/logout") {
                call.sessions.clear<UserSession>()
                LoginInstance.status = "Logged out with success!"
                call.respondRedirect("/")
            }

            delete("/user/account") {
                // Retrieve the user session
                val userSession = call.sessions.get<UserSession>()
                var statusMessage: String

                // Check if the user session is not null
                if (userSession != null) {
                    // Try to delete the user from the repository
                    try {
                        daoUsers.deleteItem(userSession.username)
                        statusMessage = "Account deleted successfully"
                        call.sessions.clear<UserSession>()
                        call.response.headers.append("HX-Redirect","/logout")
                        call.respondText(statusMessage, status = HttpStatusCode.OK)
                    } catch (e: Exception) {
                        statusMessage = "Failed to delete account: ${e.localizedMessage}"
                        call.respondText(statusMessage, status = HttpStatusCode.InternalServerError)
                    }
                } else {
                    statusMessage = "User session is missing"
                    call.respondText(statusMessage, status = HttpStatusCode.BadRequest)
                }
            }



            authenticate("auth-session") {
                put("/user/username") {
                    val userSession = call.sessions.get<UserSession>()
                    val parameters = call.receiveParameters()
                    val newUsername = parameters["newUsername"]
                    var statusMessage: String

                    try {
                        if (userSession == null) {
                            statusMessage = "User session is missing"
                            call.respondText(statusMessage, status = HttpStatusCode.BadRequest)
                            return@put
                        }

                        if (newUsername.isNullOrEmpty()) {
                            statusMessage = "New username is missing or empty"
                            call.respondText(statusMessage, status = HttpStatusCode.BadRequest)
                            return@put
                        }

                        val username = userSession.username
                        if (daoUsers.getItem(newUsername) != null) {
                            statusMessage = "New username is already taken"
                            call.respondText(statusMessage, status = HttpStatusCode.Conflict)
                            return@put
                        }

                        if (daoUsers.updateUsername(username, newUsername)) {
                            statusMessage = "Username updated successfully"
                            call.response.headers.append("HX-Redirect","/logout")
                            call.respondText(statusMessage, status = HttpStatusCode.OK)
                        } else {
                            statusMessage = "Failed to update username"
                            call.respondText(statusMessage, status = HttpStatusCode.InternalServerError)
                        }

                    } catch (e: Exception) {
                        statusMessage = "An error occurred: ${e.localizedMessage}"
                        call.respondText(statusMessage, status = HttpStatusCode.InternalServerError)
                    }
                }
            }

            authenticate("auth-session") {
                put("/user/email") {
                    val userSession = call.sessions.get<UserSession>()
                    val parameters = call.receiveParameters()
                    val newEmail = parameters["newEmail"]
                    var statusMessage: String

                    if (userSession != null && newEmail != null) {
                        val username = userSession.username
                        if (daoUsers.getByEmail(newEmail) != null) {
                            statusMessage = "New email is already taken"
                            call.respondText(statusMessage, status = HttpStatusCode.Conflict)
                            return@put
                        }

                        if (daoUsers.updateEmail(username, newEmail)) {
                            statusMessage = "Email updated successfully"
                            call.response.headers.append("HX-Redirect","/profile")
                            call.respondText(statusMessage, status = HttpStatusCode.OK)
                        } else {
                            statusMessage = "Failed to update email"
                            call.respondText(statusMessage, status = HttpStatusCode.InternalServerError)
                        }
                    } else {
                        statusMessage = "User session or new email is missing"
                        call.respondText(statusMessage, status = HttpStatusCode.BadRequest)
                    }
                }

                authenticate("auth-session") {
                    put("/user/password") {
                        val userSession = call.sessions.get<UserSession>()
                        val parameters = call.receiveParameters()
                        val newPassword = parameters["newPassword"]
                        var statusMessage: String

                        if (userSession != null && !newPassword.isNullOrBlank()) {
                            val username = userSession.username

                            val hashedPassword = newPassword.hashCode()

                            if (daoUsers.updatePassword(username, hashedPassword)) {
                                statusMessage = "Password updated successfully"
                                call.response.headers.append("HX-Redirect", "/logout")
                                call.respondText(statusMessage, status = HttpStatusCode.OK)
                            } else {
                                statusMessage = "Failed to update password"
                                call.respondText(statusMessage, status = HttpStatusCode.InternalServerError)
                            }
                        } else {
                            statusMessage = "User session or new password is missing"
                            call.respondText(statusMessage, status = HttpStatusCode.BadRequest)
                        }
                    }
                }


            put("/user/profilepicture") {
                    val userSession = call.sessions.get<UserSession>()
                    val multipartData = call.receiveMultipart()
                    val imageDataPart = multipartData.readPart() as? PartData.FileItem

                    if (userSession != null && imageDataPart != null) {
                        val username = userSession.username


                        // Extract image data as ByteArray
                        val imageData = imageDataPart.streamProvider().readBytes()


                        // Pass image data as ByteArray to updateProfilePicture function
                        if (daoUsers.updateProfilePicture(username, imageData)) {
                            call.respondText("Profile picture uploaded successfully")
                        } else {
                            call.respondText("Failed to upload profile picture")
                        }
                    } else {
                        call.respondText("User session or image data is missing")
                    }
                }



                delete("/user/profilepicture") {
                    val userSession = call.sessions.get<UserSession>()
                    var statusMessage: String

                    if (userSession != null) {
                        val username = userSession.username
                        if (daoUsers.removeProfilePicture(username)) {
                            statusMessage = "Profile picture removed successfully"
                            call.response.headers.append("HX-Redirect","/profile")
                            call.respondText(statusMessage, status = HttpStatusCode.OK)
                        } else {
                            statusMessage = "Failed to remove profile picture"
                            call.respondText(statusMessage, status = HttpStatusCode.InternalServerError)
                        }
                    } else {
                        statusMessage = "User session is missing"
                        call.respondText(statusMessage, status = HttpStatusCode.BadRequest)
                    }
                }

                get("/hogwards-house") {
                    val userSession = call.sessions.get<UserSession>()
                    if (userSession != null) {
                        val username = userSession.username
                        val house = daoUsers.getHouse(username)
                        if (house != null) {
                            call.respondText(house)
                        } else {
                            call.respond(HttpStatusCode.NotFound, "House not found")
                        }
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "User session is missing")
                    }
                }



                get("/profile-picture") {
                    val userSession = call.sessions.get<UserSession>()
                    if (userSession != null) {
                        val username = userSession.username
                        val profilePictureData = daoUsers.getProfilePictureData(username)
                        if (profilePictureData != null) {
                            call.respondBytes(profilePictureData, ContentType.Image.JPEG)
                        } else {
                            // Respond with an empty byte array if no profile picture is set
                            call.respondBytes(ByteArray(0), ContentType.Image.JPEG)
                        }
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "User session is missing")
                    }
                }
            }
        }
    }
}



