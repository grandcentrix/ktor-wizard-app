package net.grandcentrix.backend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.controllers.UserSession

suspend fun ApplicationCall.verifyUserSession(): UserSession? {
    val userSession = sessions.get<UserSession>()
    if (userSession == null) {
        respondText("User session is missing", status = HttpStatusCode.BadRequest)
    }
    return userSession
}

suspend fun ApplicationCall.updateUsername(userSession: UserSession, newUsername: String?) {
    if (newUsername.isNullOrEmpty()) {
        respondText("New username is missing or empty", status = HttpStatusCode.BadRequest)
        return
    }
    val username = userSession.username
    if (daoUsers.getItem(newUsername) != null) {
        respondText("New username is already taken", status = HttpStatusCode.Conflict)
        return
    }
    if (daoUsers.updateUsername(username, newUsername)) {
        response.headers.append("HX-Redirect", "/logout")
        respondText("Username updated successfully", status = HttpStatusCode.OK)
    } else {
        respondText("Failed to update username", status = HttpStatusCode.InternalServerError)
    }
}

suspend fun ApplicationCall.updateEmail(userSession: UserSession, newEmail: String?) {
    if (newEmail.isNullOrEmpty()) {
        respondText("New email is missing or empty", status = HttpStatusCode.BadRequest)
        return
    }
    val username = userSession.username
    if (daoUsers.getByEmail(newEmail) != null) {
        respondText("New email is already taken", status = HttpStatusCode.Conflict)
        return
    }
    if (daoUsers.updateEmail(username, newEmail)) {
        response.headers.append("HX-Redirect", "/profile")
        respondText("Email updated successfully", status = HttpStatusCode.OK)
    } else {
        respondText("Failed to update email", status = HttpStatusCode.InternalServerError)
    }
}


@OptIn(ExperimentalStdlibApi::class)
suspend fun ApplicationCall.updatePassword(userSession: UserSession, newPassword: String?) {
    if (newPassword.isNullOrBlank()) {
        respondText("New password is missing or empty", status = HttpStatusCode.BadRequest)
        return
    }
    val username = userSession.username
    val salt = SignupInstance.generateRandomSalt()
    val hashedPassword = SignupInstance.generateHash(newPassword, salt)
    val hexSalt = salt.toHexString()
    if (daoUsers.updatePassword(username, hexSalt + hashedPassword)) {
        response.headers.append("HX-Redirect", "/logout")
        respondText("Password updated successfully", status = HttpStatusCode.OK)
    } else {
        respondText("Failed to update password", status = HttpStatusCode.InternalServerError)
    }
}

suspend fun ApplicationCall.deleteAccount(userSession: UserSession) {
    try {
        daoUsers.deleteItem(userSession.username)
        sessions.clear<UserSession>()
        response.headers.append("HX-Redirect", "/logout")
        respondText("Account deleted successfully", status = HttpStatusCode.OK)
    } catch (e: Exception) {
        respondText("Failed to delete account: ${e.localizedMessage}", status = HttpStatusCode.InternalServerError)
    }
}

suspend fun ApplicationCall.updateProfilePicture(userSession: UserSession, imageData: ByteArray?) {
    if (imageData == null) {
        respondText("Image data is missing", status = HttpStatusCode.BadRequest)
        return
    }
    if (daoUsers.updateProfilePicture(userSession.username, imageData)) {
        respondText("Profile picture uploaded successfully", status = HttpStatusCode.OK)
    } else {
        respondText("Failed to upload profile picture", status = HttpStatusCode.InternalServerError)
    }
}

suspend fun ApplicationCall.removeProfilePicture(userSession: UserSession) {
    if (daoUsers.removeProfilePicture(userSession.username)) {
        response.headers.append("HX-Redirect", "/profile")
        respondText("Profile picture removed successfully", status = HttpStatusCode.OK)
    } else {
        respondText("Failed to remove profile picture", status = HttpStatusCode.InternalServerError)
    }
}

suspend fun ApplicationCall.getHogwartsHouse(userSession: UserSession) {
    val house = daoUsers.getHouse(userSession.username)
    if (house != null) {
        respondText(house, status = HttpStatusCode.OK)
    } else {
        respondText("House not found", status = HttpStatusCode.NotFound)
    }
}

suspend fun ApplicationCall.getProfilePicture(userSession: UserSession) {
    val profilePictureData = daoUsers.getProfilePictureData(userSession.username)
    if (profilePictureData != null) {
        respondBytes(profilePictureData, ContentType.Image.JPEG)
    } else {
        respondBytes(ByteArray(0), ContentType.Image.JPEG)
    }
}
