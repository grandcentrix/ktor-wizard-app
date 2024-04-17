package net.grandcentrix.backend.controllers

import io.ktor.server.auth.*
import net.grandcentrix.backend.repository.UserManager.Companion.UserManagerInstance


class Login {
    companion object {
        val LoginInstance: Login = Login()
    }

    var status = ""

    fun verifyLogin(credential: UserPasswordCredential): Boolean {
        UserManagerInstance.getAll().forEach {
            if (it.username == credential.name && it.password == credential.password) {
//                LoginInstance.status = "Login successful"
                return true
            }
        }
//        LoginInstance.status = "Username and password didn't match!"
        return false
    }

}

// stores the session data
data class UserSession(val username: String): Principal