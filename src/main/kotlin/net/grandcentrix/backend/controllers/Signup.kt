package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.util.*
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.UserAlreadyExistsException
import net.grandcentrix.backend.repository.HousesRepository.Companion.HousesRepositoryInstance
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.text.toCharArray

class Signup {

    companion object {
        val SignupInstance: Signup = Signup()
        private const val ALGORITHM = "PBKDF2WithHmacSHA512"
        private const val ITERATIONS = 120_000
        private const val KEY_LENGTH = 256
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

        val salt = generateRandomSalt()
        val hashedPassword = generateHash(password, salt)
        val hexSalt = salt.toHexString()

        verifyDuplicates(email, username)

        if (house.isNullOrBlank()) {
            val user = User(
                2, //TODO assign unique ID automatically
                name,
                surname,
                email,
                username,
                hexSalt+hashedPassword,
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
                hexSalt+hashedPassword,
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

    private fun generateRandomSalt(): ByteArray {
        val random = SecureRandom() // PRF?
        val salt = ByteArray(16) // creates a 16-byte salt
        random.nextBytes(salt)
        return salt
    }

     fun ByteArray.toHexString(): String = hex(this) // convert byte array to a hex string

     fun generateHash(password: String, salt: ByteArray): String {
        // Returns a SecretKeyFactory object that converts secret keys of the specified algorithm
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH) // derived key specifications
        val key: SecretKey = factory.generateSecret(spec) // generates the key through the chosen algorithm using the key spec
        val hash: ByteArray = key.encoded // encodes the key to byte array
        return hash.toHexString() // transforms the encoded key to a hex string
    }
}