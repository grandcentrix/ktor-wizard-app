package net.grandcentrix.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.util.*
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.controllers.UserSession

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
    } catch (e: Exception) {
        throw RequestException("Failed to upload profile picture: ${e.localizedMessage}")
    }
}

suspend fun ApplicationCall.removeProfilePicture(userSession: UserSession) {
    try {
        daoUsers.removeProfilePicture(userSession.username)
        response.headers.append("HX-Redirect", "/profile")
        respondText("Profile picture removed successfully", status = HttpStatusCode.OK)
    } catch (e: Exception) {
        throw RequestException("Failed to remove profile picture: ${e.localizedMessage}")
    }
}

suspend fun ApplicationCall.getHogwartsHouse(userSession: UserSession) {
    val house = daoUsers.getHouse(userSession.username)
    if (house != null) {
        respondText(house, status = HttpStatusCode.OK)
    } else {
        throw RequestException("House not found")
    }
}

suspend fun ApplicationCall.getProfilePicture(userSession: UserSession) {
    val profilePictureData = daoUsers.getProfilePictureData(userSession.username)
    if (profilePictureData?.isNotEmpty()!!) {
        respondBytes(profilePictureData, ContentType.Image.JPEG)
    } else {
        respond(HttpStatusCode.NotFound, "Profile picture not found")
    }
}

