package net.grandcentrix.backend.dao

import net.grandcentrix.backend.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Characters : Table("characters") {
    val id = varchar("id", 255) // varchar for string type
    val name = varchar("name", 255)
    val wiki = varchar("wiki", 255)
    val aliasNames = text("alias_names")
    val animagus = varchar("animagus", 255)
    val boggart = varchar("boggart", 255)
    val patronus = varchar("patronus", 255)
    val birth = varchar("birth", 255)
    val death = varchar("death", 255)
    val familyMembers = text("family_members")
    val house = varchar("house", 255)
    val imageUrl = varchar("image_url", 255)
    val jobs = text("jobs")
    val nationality = varchar("nationality", 255)
    val slug = varchar("slug", 255)
    val species = varchar("species", 255)
    val titles = text("titles")
    val wands = text("wands")
    val gender = varchar("gender", 255)
    val bloodStatus = varchar("blood_status", 255)
}

class DAOApi {

    fun saveCharacters(characters: List<Character>) = transaction {
        characters.forEach { character ->
            val existingCharacter = Characters.select { Characters.name eq character.name }.firstOrNull()
            if (existingCharacter == null) {
                Characters.insert {
                    it[id] = character.id?: ""
                    it[name] = character.name?: ""
                    it[wiki] = character.wiki?: ""
                    it[aliasNames] = character.aliasNames?.joinToString(separator = ",")?: ""
                    it[animagus] = character.animagus?: ""
                    it[boggart] = character.boggart?: ""
                    it[patronus] = character.patronus?: ""
                    it[birth] = character.birth?: ""
                    it[death] = character.death?: ""
                    it[familyMembers] = character.familyMembers?.joinToString(separator = ",")?: ""
                    it[house] = character.house?: ""
                    it[imageUrl] = character.imageUrl?: ""
                    it[jobs] = character.jobs?.joinToString(separator = ",")?: ""
                    it[nationality] = character.nationality?: ""
                    it[slug] = character.slug?: ""
                    it[species] = character.species?: ""
                    it[titles] = character.titles?.joinToString(separator = ",")?: ""
                    it[wands] = character.wands?.joinToString(separator = ",")?: ""
                    it[gender] = character.gender?: ""
                    it[bloodStatus] = character.blood_status?: ""
                }
            }
        }
    }

    fun getCharacters(): List<Character> = transaction {
        Characters.selectAll().map { row ->
            Character(
                id = row[Characters.id]?: "",
                name = row[Characters.name]?: "",
                wiki = row[Characters.wiki]?: "",
                aliasNames = row[Characters.aliasNames]?.split(","),
                animagus = row[Characters.animagus]?: "",
                boggart = row[Characters.boggart]?: "",
                patronus = row[Characters.patronus]?: "",
                birth = row[Characters.birth]?: "",
                death = row[Characters.death]?: "",
                familyMembers = row[Characters.familyMembers]?.split(","),
                house = row[Characters.house]?: "",
                imageUrl = row[Characters.imageUrl]?: "",
                jobs = row[Characters.jobs]?.split(","),
                nationality = row[Characters.nationality]?: "",
                slug = row[Characters.slug]?: "",
                species = row[Characters.species]?: "",
                titles = row[Characters.titles]?.split(","),
                wands = row[Characters.wands]?.split(","),
                gender = row[Characters.gender]?: "",
                blood_status = row[Characters.bloodStatus]?: "",
            )
        }
    }
}

val daoCharacters = DAOApi()