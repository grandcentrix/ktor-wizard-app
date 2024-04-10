package net.grandcentrix.backend


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import kotlinx.html.*
import java.io.File
import java.util.*

data class User(val username: String, val password: String, val profilePicture: String?, val house: House)

enum class House {
    GRYFFINDOR,
    HUFFLEPUFF,
    RAVENCLAW,
    SLYTHERIN
}

val usersFile = File("users.json")
val users = mutableListOf<User>()


fun loadUsers(): List<User> {
    if (!usersFile.exists()) {
        usersFile.createNewFile()
    }
    return try {
        jacksonObjectMapper().readValue(usersFile)
    } catch (e: Exception) {
        emptyList()
    }
}

fun saveUsers() {
    usersFile.writeText(jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(users))
}

fun Application.configureRouting() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Sessions) {
        cookie<SessionUser>("SESSION")
    }
    install(Routing) {
        route("/") {
            get {
                call.respondHtml {
                    head {
                        title("Login/Register System")
                        style {
                            unsafe {
                                raw("""
                .profile-picture {
                    width: 50px;
                    height: 50px;
                    border-radius: 50%;
                    cursor: pointer;
                    margin-right: 20px;
                }
            """.trimIndent())
                            }
                        }
                    }
                    body {
                        // Display profile picture here
                        displayProfilePicture(call)

                        h1 {
                            +"Welcome to the login/register system!"
                        }
                        p {
                            +"Register:"
                            form(action = "/register", method = FormMethod.post, encType = FormEncType.multipartFormData) {
                                label { +"Username: " }
                                textInput { name = "username" }
                                br
                                label { +"Password: " }
                                passwordInput { name = "password" }
                                br
                                label { +"Profile Picture: " }
                                fileInput { name = "profilePicture" }
                                br
                                label { +"House: " }
                                select {
                                    name = "house" // Add name attribute here
                                    option { +"Gryffindor" }
                                    option { +"Hufflepuff" }
                                    option { +"Ravenclaw" }
                                    option { +"Slytherin" }
                                }
                                br
                                submitInput { value = "Register" }
                            }
                        }
                        p {
                            +"Login:"
                            form(action = "/login", method = FormMethod.post) {
                                label { +"Username: " }
                                textInput { name = "username" }
                                br
                                label { +"Password: " }
                                passwordInput { name = "password" }
                                br
                                submitInput { value = "Login" }
                            }
                        }
                    }
                }
            }
            post("/register") {
                val parameters = call.receiveParameters()
                val username = parameters["username"]
                val password = parameters["password"]
                val house = parameters["house"]

                if (username.isNullOrEmpty() || password.isNullOrEmpty() || house.isNullOrEmpty()) {
                    call.respond(HttpStatusCode.BadRequest, "Missing required fields")
                    return@post
                }

                val capturedUsername = username
                val capturedPassword = password
                val capturedHouse = house?.let { House.valueOf(it.toUpperCase(Locale.ENGLISH)) } ?: House.GRYFFINDOR

                if (users.any { it.username == capturedUsername }) {
                    call.respond(HttpStatusCode.BadRequest, "Username already exists")
                    return@post
                }

                val profilePicture = parameters["profilePicture"]

                users.add(User(capturedUsername!!, capturedPassword!!, profilePicture, capturedHouse))
                saveUsers()
                call.respondText("User registered successfully")
            }
            post("/login") {
                val parameters = call.receiveParameters()
                val username = parameters["username"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing username")
                val password = parameters["password"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing password")

                val user = users.find { it.username == username && it.password == password }
                if (user != null) {
                    call.sessions.set(SessionUser(user))
                    call.respondRedirect("/profile")
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid username or password")
                }
            }
        }
        route("/profile") {
            get {
                val sessionUser = call.sessions.get<SessionUser>()
                if (sessionUser == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Please login to access profile")
                } else {
                    val user = users.find { it.username == sessionUser.user.username }
                    if (user == null) {
                        call.respond(HttpStatusCode.InternalServerError, "User not found")
                    } else {
                        call.respondHtml {
                            head {
                                title("Profile")
                            }
                            body {
                                // Display profile picture here
                                displayProfilePicture(call)

                                h1 {
                                    +"Welcome, ${user.username}!"
                                }
                                p {
                                    +"You are in ${user.house} house"
                                }
                                if (user.profilePicture != null) {
                                    img(src = "/static/${user.profilePicture}") {
                                        width = "100"
                                        height = "100"
                                    }
                                }
                                form(action = "/upload", method = FormMethod.post, encType = FormEncType.multipartFormData) {
                                    label { +"Change Profile Picture: " }
                                    fileInput { name = "profilePicture" }
                                    br
                                    submitInput { value = "Upload" }
                                }
                                p {
                                    a(href = "/logout") {
                                        +"Logout"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        post("/upload") {
            val sessionUser = call.sessions.get<SessionUser>()
            if (sessionUser == null) {
                call.respond(HttpStatusCode.Unauthorized, "Please login to upload profile picture")
            } else {
                val multipart = call.receiveMultipart()
                val parts = multipart.readAllParts()

                var profilePicture: String? = null

                parts.forEach { part ->
                    if (part is PartData.FileItem) {
                        val fileName = "${UUID.randomUUID()}-${part.originalFileName}"
                        val file = File(fileName)
                        part.streamProvider().use { input ->
                            file.outputStream().buffered().use { output ->
                                input.copyTo(output)
                            }
                        }
                        profilePicture = fileName
                    }
                }

                var user = users.find { it.username == sessionUser.user.username }
                if (user != null) {
                    user = user.copy(profilePicture = profilePicture)
                    saveUsers()
                    call.respondRedirect("/profile")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "User not found")
                }
            }
        }

        get("/static/{fileName}") {
            val fileName = call.parameters["fileName"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid file name")
            val file = File(fileName)
            if (file.exists()) {
                call.respondFile(file)
            } else {
                call.respond(HttpStatusCode.NotFound, "File not found")
            }
        }

        get("/logout") {
            call.sessions.clear<SessionUser>()
            call.respondRedirect("/")
        }
    }
}

fun FlowContent.displayProfilePicture(call: ApplicationCall) {
    div {
        style = "display: flex; justify-content: flex-end; align-items: center; padding: 10px;"
        a(href = "/profile") {
            val sessionUser = call.sessions.get<SessionUser>()
            val profilePictureUrl = sessionUser?.user?.profilePicture?.let { "/static/$it" }
            img(classes = "profile-picture", src = profilePictureUrl ?: "/placeholder.jpg") {
                width = "50"
                height = "50"
                style = "border-radius: 50%;"
            }
        }
    }
}


data class SessionUser(val user: User)