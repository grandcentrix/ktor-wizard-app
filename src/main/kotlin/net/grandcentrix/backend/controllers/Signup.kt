package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.util.*
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.RequestException
import net.grandcentrix.backend.plugins.UserAlreadyExistsException
import net.grandcentrix.backend.repository.HousesRepository.Companion.HousesRepositoryInstance
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.regex.Pattern
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.text.toCharArray

class Signup {

    companion object {
        val SignupInstance: Signup = Signup()
        private const val ALGORITHM = "PBKDF2WithHmacSHA512"
        private const val ITERATIONS = 120_000
        private const val KEY_LENGTH_BYTES = 32
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

        val salt = generateRandomSalt()
        val hashedPassword = generateHash(password, salt)
        val hexSalt = salt.toHexString()

        verifyDuplicates(email, username)

        if (house.isNullOrBlank()) {
            val user = User(
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
                name,
                surname,
                email,
                username,
                hexSalt+hashedPassword,
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

    private fun generateRandomSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16) // creates a 16-byte salt
        random.nextBytes(salt)
        return salt
    }

     fun ByteArray.toHexString(): String = hex(this) // convert byte array to a hex string

     fun generateHash(password: String, salt: ByteArray): String {
        // Returns a SecretKeyFactory object that converts secret keys of the specified algorithm
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH_BYTES) // derived key specifications
        val key: SecretKey = factory.generateSecret(spec) // generates the key through the chosen algorithm using the key spec
        val hash: ByteArray = key.encoded // encodes the key to byte array
        return hash.toHexString() // transforms the encoded key to a hex string
    }
}