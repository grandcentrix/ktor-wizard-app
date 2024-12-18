package net.grandcentrix.backend.controllers

import io.ktor.http.*
import io.ktor.util.*
import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.User
import net.grandcentrix.backend.plugins.RequestException
import net.grandcentrix.backend.plugins.SignupException
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
            house.isNullOrBlank() ||
            email.isNullOrBlank()
        ) {
            throw RequestException("Missing required fields!")
        }

        verifyName(name)
        verifySurname(surname)
        verifyUsername(username)
        verifyEmail(email)

        val salt = generateRandomSalt()
        val hashedPassword = generateHash(password, salt)
        val hexSalt = salt.toHexString()

        val user = User(
            name,
            surname,
            email,
            username,
            hexSalt + hashedPassword,
            house
        )
        daoUsers.addItem(user)
    }

    private fun verifyEmail(email: String) {

        email.toCharArray().map { character ->
            if (!isAlphanumeric(character) &&
                (character.toString() != "@" && character.toString() != ".")
            ) {
                throw SignupException(
                    "E-mail contain invalid characters."
                )
            }
        }

        if (
            email.startsWith("@") ||
            email.startsWith(".") ||
            email.endsWith("@") ||
            email.endsWith(".") ||
            (!email.contains("@") && !email.contains(".")) ||
            email.contains("@.")
        ) {
            throw SignupException("Must be a valid format for e-mail address.")
        }

    }

    private fun verifyName(name: String) {
        name.toCharArray().map { character ->
            if (!character.isLetter() && !character.isWhitespace()) {
                throw SignupException("Name and surname must contain only letters.")
            }
        }
    }

    private fun verifySurname(surname: String) {
        surname.toCharArray().map { character ->
            if (!character.isLetter() && !character.isWhitespace()) {
                throw SignupException("Name and surname must contain only letters.")
            }
        }
    }

    private fun verifyUsername(username: String) {

        username.toCharArray().map { character ->
            if (!isAlphanumeric(character) &&
                (character.toString() != "." && character.toString() != "_")
            ) {
                throw SignupException(
                    "Username can only contain alphanumeric, underscore and point characters."
                )
            }
        }

        if (
            username.endsWith(".") ||
            username.startsWith(".") ||
            username.startsWith("_") ||
            username.length > 25
        ) {
            throw SignupException(
                "Username can't start with non-alphanumeric characters or be more than 25 characters."
            )
        }
    }

    private fun isAlphanumeric(character: Char) =
        character in '0'..'9' ||
                character in 'A'..'Z' ||
                character in 'a'..'z'


    fun generateRandomSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16) // creates a 16-byte salt
        random.nextBytes(salt)
        return salt
    }

    fun ByteArray.toHexString(): String = hex(this) // convert byte array to a hex string

    fun generateHash(password: String, salt: ByteArray): String {
        // Returns a SecretKeyFactory object that converts secret keys of the specified algorithm
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        val spec: KeySpec =
            PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH_BYTES) // derived key specifications
        val key: SecretKey =
            factory.generateSecret(spec) // generates the key through the chosen algorithm using the key spec
        val hash: ByteArray = key.encoded // encodes the key to byte array
        return hash.toHexString() // transforms the encoded key to a hex string
    }
}