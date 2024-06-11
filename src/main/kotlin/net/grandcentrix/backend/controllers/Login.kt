package net.grandcentrix.backend.controllers

import io.ktor.server.auth.*
import net.grandcentrix.backend.controllers.Signup.Companion.SignupInstance
import net.grandcentrix.backend.dao.daoUsers


class Login {
    companion object {
        val LoginInstance: Login = Login()
        private const val SALT_LENGTH_BYTES = 16
    }


    fun verifyLogin(credential: UserPasswordCredential): Boolean {
        val user = daoUsers.getItem(credential.name)
        if (user == null) {
            return false
        } else if (verifyPassword(credential.password, user.password)) {
            return true
        }
        return false
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun verifyPassword(password: String, hashedPassword: String): Boolean {
        val hashedPasswordBytes = hashedPassword.hexToByteArray()
        val salt = hashedPasswordBytes.copyOfRange(0, SALT_LENGTH_BYTES)
        val storedPasswordHash = hashedPasswordBytes.copyOfRange(SALT_LENGTH_BYTES, hashedPasswordBytes.size).toHexString()

        val inputHash = SignupInstance.generateHash(password, salt)
        return inputHash.contentEquals(storedPasswordHash)
    }

}

// stores the session data
data class UserSession(val username: String): Principal