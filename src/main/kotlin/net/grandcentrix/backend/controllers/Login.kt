package net.grandcentrix.backend.controllers

import io.ktor.server.auth.*
import net.grandcentrix.backend.dao.daoUsers


class Login {
    companion object {
        val LoginInstance: Login = Login()
    }


    fun verifyLogin(credential: UserPasswordCredential): Boolean {
        daoUsers.getAll().forEach {
            if (it.username == credential.name && it.password == credential.password.hashCode()) {
                return true
            }
        }
        return false
    }

}

// stores the session data
data class UserSession(val username: String): Principal