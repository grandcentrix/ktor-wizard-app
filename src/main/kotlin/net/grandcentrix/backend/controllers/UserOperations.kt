package net.grandcentrix.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.controllers.UserSession
import net.grandcentrix.backend.dao.daoUsers
import java.net.URL


fun ApplicationCall.verifyUserSession(): UserSession? {
    val userSession = sessions.get<UserSession>()
    if (userSession == null) {
        throw RequestException("User session is missing")
    }
    return userSession
}

suspend fun ApplicationCall.updateUsername(userSession: UserSession, newUsername: String?) {
    if (newUsername.isNullOrEmpty()) {
        throw RequestException("New username is missing or empty")
    }
    val username = userSession.username
    if (daoUsers.getItem(newUsername) != null) {
        throw UserAlreadyExistsException("New username is already taken")
    }
    try {
        daoUsers.updateUsername(username, newUsername)
        response.headers.append("HX-Redirect", "/logout")
        respondText("Username updated successfully", status = HttpStatusCode.OK)
    } catch (e: IllegalArgumentException) {
        throw RequestException("Invalid argument: ${e.localizedMessage}")
    } catch (e: DAOUsersException) {
        throw RequestException("Database error: ${e.localizedMessage}")
    } catch (e: Exception) {
        throw RequestException("Failed to update username: ${e.localizedMessage}")
    }
}

suspend fun ApplicationCall.updateEmail(userSession: UserSession, newEmail: String?) {
    if (newEmail.isNullOrEmpty()) {
        throw RequestException("New email is missing or empty")
    }
    val username = userSession.username
    if (daoUsers.getByEmail(newEmail) != null) {
        throw UserAlreadyExistsException("New email is already taken")
    }
    try {
        daoUsers.updateEmail(username, newEmail)
        response.headers.append("HX-Redirect", "/profile")
        respondText("Email updated successfully", status = HttpStatusCode.OK)
    } catch (e: IllegalArgumentException) {
        throw RequestException("Invalid argument: ${e.localizedMessage}")
    } catch (e: DAOUsersException) {
        throw DAOUsersException("Database error: ${e.localizedMessage}")
    } catch (e: Exception) {
        throw RequestException("Failed to update email: ${e.localizedMessage}")
    }
}

suspend fun ApplicationCall.updatePassword(userSession: UserSession, newPassword: String?) {
    if (newPassword.isNullOrBlank()) {
        throw RequestException("New password is missing or empty")
    }
    val username = userSession.username
    val salt = SignupInstance.generateRandomSalt()
    val hashedPassword = SignupInstance.generateHash(newPassword, salt)
    val hexSalt = hex(salt)
    try {
        daoUsers.updatePassword(username, hexSalt + hashedPassword)
        response.headers.append("HX-Redirect", "/logout")
        respondText("Password updated successfully", status = HttpStatusCode.OK)
    } catch (e: IllegalArgumentException) {
        throw RequestException("Invalid argument: ${e.localizedMessage}")
    } catch (e: DAOUsersException) {
    throw DAOUsersException("Database error: ${e.localizedMessage}")
    } catch (e: Exception) {
        throw RequestException("Failed to update password: ${e.localizedMessage}")
    }
}

suspend fun ApplicationCall.deleteAccount(userSession: UserSession) {
    try {
        daoUsers.deleteItem(userSession.username)
        sessions.clear<UserSession>()
        response.headers.append("HX-Redirect", "/logout")
        respondText("Account deleted successfully", status = HttpStatusCode.OK)
    } catch (e: IllegalArgumentException) {
        throw RequestException("Invalid argument: ${e.localizedMessage}")
    } catch (e: DAOUsersException) {
        throw DAOUsersException("Database error: ${e.localizedMessage}")
    } catch (e: Exception) {
        throw RequestException("Failed to delete account: ${e.localizedMessage}")
    }
}

suspend fun ApplicationCall.updateProfilePicture(userSession: UserSession, imageData: ByteArray?) {
    if (imageData == null) {
        throw RequestException("Image data is missing")
    }
    try {
        daoUsers.updateProfilePicture(userSession.username, imageData)
        respondText("Profile picture uploaded successfully", status = HttpStatusCode.OK)
    } catch (e: IllegalArgumentException) {
        throw RequestException("Invalid argument: ${e.localizedMessage}")
    } catch (e: DAOUsersException) {
        throw DAOUsersException("Database error: ${e.localizedMessage}")
    } catch (e: Exception) {
        throw RequestException("Failed to upload profile picture: ${e.localizedMessage}")
    }
}

suspend fun ApplicationCall.removeProfilePicture(userSession: UserSession) {
    try {
        daoUsers.removeProfilePicture(userSession.username)
        response.headers.append("HX-Redirect", "/profile")
        respondText("Profile picture removed successfully", status = HttpStatusCode.OK)
    } catch (e: IllegalArgumentException) {
        throw RequestException("Invalid argument: ${e.localizedMessage}")
    } catch (e: DAOUsersException) {
        throw DAOUsersException("Database error: ${e.localizedMessage}")
    } catch (e: Exception) {
        throw RequestException("Failed to remove profile picture: ${e.localizedMessage}")
    }
}

suspend fun ApplicationCall.getHogwartsHouse(userSession: UserSession) {
    try {
        val house = daoUsers.getHouse(userSession.username)
        if (house != null) {
            respondText(house, status = HttpStatusCode.OK)
        } else {
            throw RequestException("House not found")
        }
    } catch (e: DAOUsersException) {
        throw DAOUsersException("Database error: ${e.localizedMessage}")
    } catch (e: Exception) {
        throw RequestException("Failed to retrieve house: ${e.localizedMessage}")
    }
}

suspend fun ApplicationCall.getProfilePicture(userSession: UserSession) {
    try {
        val profilePictureData = daoUsers.getProfilePictureData(userSession.username)
        if (profilePictureData?.isNotEmpty() == true) {
            respondBytes(profilePictureData, ContentType.Image.JPEG)
        } else {
            val inputStream = URL("https://as2.ftcdn.net/v2/jpg/02/15/84/43/1000_F_215844325_ttX9YiIIyeaR7Ne6EaLLjMAmy4GvPC69.jpg").openStream()
            val defaultProfilePicture = inputStream.readBytes()
            respondBytes(defaultProfilePicture, ContentType.Image.JPEG)
        }
    } catch (e: DAOUsersException) {
        throw DAOUsersException("Database error: ${e.localizedMessage}")
    } catch (e: Exception) {
        respond(HttpStatusCode.NotFound, "Failed to retrieve profile picture: ${e.localizedMessage}")
    }
}
