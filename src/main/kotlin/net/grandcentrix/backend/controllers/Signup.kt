package net.grandcentrix.backend.controllers

import io.ktor.http.*
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.RequestException
import net.grandcentrix.backend.plugins.UserAlreadyExistsException
import net.grandcentrix.backend.repository.HousesRepository.Companion.HousesRepositoryInstance
import java.util.regex.Pattern

class Signup {

    companion object {
        val SignupInstance: Signup = Signup()
    }

    fun createUser(formParameters: Parameters) {

        val name = formParameters["name"]
        val surname = formParameters["surname"]
        val email = formParameters["email"]
        val username = formParameters["username"]
        val password = formParameters["password"]
        val house = formParameters["houses"]

         if (
             name.isNullOrBlank() ||
             surname.isNullOrBlank() ||
             username.isNullOrBlank() ||
             password.isNullOrBlank() ||
             email.isNullOrBlank()
         ) {
             throw RequestException("Missing required fields!")
         }

        verifyFields(name, surname, username, email)

        val hashedPassword = password.hashCode()

        verifyDuplicates(email, username)

        if (house.isNullOrBlank()) {
            val user = User(
                2, //TODO assign unique ID automatically
                name,
                surname,
                email,
                username,
                hashedPassword,
                null
            )
            daoUsers.addItem(user)
        } else {
            val user = User(
                2,
                name,
                surname,
                email,
                username,
                hashedPassword,
                HousesRepositoryInstance.getItem(house)
            )
            daoUsers.addItem(user)
        }
    }

    private fun verifyFields(
        name: String,
        surname: String,
        username: String,
        email: String
    ) {
        // regex - set of strings that matches the pattern
        val emailPattern = Pattern.compile("^(.+)@(\\S+)$")
        val usernamePattern = Pattern.compile("^(^[^-._,\\s])(\\S+)(\\w\$)\$")
        val namesPattern = Pattern.compile("^[a-zA-Z]+(?:\\s+[a-zA-Z]+)*\$")

        if (!emailPattern.matcher(email).matches()) {
            throw RequestException("Invalid value for e-mail!")
        } else if (!usernamePattern.matcher(username).matches()) {
            throw RequestException("Invalid value for username!")
        } else if (!namesPattern.matcher(name).matches() || !namesPattern.matcher(surname).matches()) {
            throw RequestException("Invalid value for name!")
        }
    }

    private fun verifyDuplicates(email: String, username: String) {
        if (daoUsers.getByEmail(email) != null) {
            throw UserAlreadyExistsException("Email is already in use!")
        }
        if (daoUsers.getItem(username) != null) {
            throw UserAlreadyExistsException("Username is already in use!")
        }
    }
}