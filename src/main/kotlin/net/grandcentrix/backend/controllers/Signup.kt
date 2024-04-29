package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.plugins.*
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.UserAlreadyExistsException
import net.grandcentrix.backend.repository.HousesRepository.Companion.HousesRepositoryInstance

class Signup {

    companion object {
        val SignupInstance: Signup = Signup()
    }

    var status = ""

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
            email.isNullOrBlank())
        {
            status = "Required fields cannot be empty!"
            throw MissingRequestParameterException("Missing required fields!")
        }

        val hashedPassword = password.hashCode()

        verifyDuplicates(email, username)
//        val id = UUID.randomUUID().toString()

        if (house.isNullOrBlank()) {
            val user = User(
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
                name,
                surname,
                email,
                username,
                hashedPassword,
                HousesRepositoryInstance.getItem(house)
            )
            daoUsers.addItem(user)
        }

        status = "Account created with success!"

    }

    private fun verifyDuplicates(email: String, username: String) {
        if (daoUsers.getByEmail(email) != null) {
            status = "Email is already in use!"
            throw UserAlreadyExistsException("Email is already in use!")
        }

        if (daoUsers.getItem(username) != null) {
            status = "Username is already in use!"
            throw UserAlreadyExistsException("Username is already in use!")
        }
    }
}