package net.grandcentrix.backend.dao

import kotlinx.serialization.SerialName
import net.grandcentrix.backend.models.*
import org.jetbrains.exposed.sql.*
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

object Potions : Table("potions") {
    val id = varchar("id", 255)
    val name = varchar("name", 255)
    val characteristics = text("characteristics")
    val difficulty = varchar("difficulty", 255)
    val effect = varchar("effect", 255)
    val wiki = varchar("wiki", 255)
    val imageUrl = varchar("image_url", 255)
    val inventors = text("inventors")
    val ingredients = text("ingredients")
    val manufacturers = text("manufacturers")
    val sideEffects = text("side_effects")
    val slug = varchar("slug", 255)
    val time = varchar("time", 255)
}

object Spells : Table("spells") {
    val id = varchar("id", 255)
    val name = varchar("name", 255)
    val category = varchar("category", 255)
    val creator = varchar("creator", 255)
    val effect = varchar("effect", 255)
    val hand = varchar("hand", 255)
    val wiki = varchar("wiki", 255)
    val imageUrl = varchar("image_url", 255)
    val incantation = varchar("incantation", 255)
    val light = varchar("light", 255)
    val slug = varchar("slug", 255)
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

    fun savePotions(potions: List<Potion>) = transaction {
        potions.forEach { potion ->
            val existingPotion = Potions.select { Potions.name eq potion.name }.firstOrNull()
            if (existingPotion == null) {
                Potions.insert {
                    it[id] = potion.id?: ""
                    it[name] = potion.name?: ""
                    it[characteristics] = potion.characteristics?.split(",")?.joinToString(separator = ",")?: ""
                    it[difficulty] = potion.difficulty?: ""
                    it[effect] = potion.effect?: ""
                    it[wiki] = potion.wiki?: ""
                    it[imageUrl] = potion.imageUrl?: ""
                    it[inventors] = potion.inventors?.split(",")?.joinToString(separator = ",")?: ""
                    it[ingredients] = potion.ingredients?.split(",")?.joinToString(separator = ",")?: ""
                    it[manufacturers] = potion.manufacturers?.split(",")?.joinToString(separator = ",")?: ""
                    it[sideEffects] = potion.sideEffects?.split(",")?.joinToString(separator = ",")?: ""
                    it[slug] = potion.slug?: ""
                    it[time] = potion.time?: ""
                }
            }
        }
    }

    fun saveSpells(spells: List<Spell>) = transaction {
        spells.forEach { spell ->
            val existingSpell = Spells.select { Spells.name eq spell.name }.firstOrNull()
            if (existingSpell == null) {
                Spells.insert {
                    it[id] = spell.id?: ""
                    it[name] = spell.name
                    it[category] = spell.category?: ""
                    it[creator] = spell.creator?: ""
                    it[effect] = spell.effect?: ""
                    it[hand] = spell.hand?: ""
                    it[wiki] = spell.wiki
                    it[imageUrl] = spell.imageUrl?: ""
                    it[incantation] = spell.incantation?: ""
                    it[light] = spell.light?: ""
                    it[slug] = spell.slug?: ""
                }
            }
        }
    }

    fun getCharacters(): List<Character> = transaction {
        Characters.selectAll().map { row ->
            Character(
                id = row[Characters.id],
                name = row[Characters.name],
                wiki = row[Characters.wiki],
                aliasNames = row[Characters.aliasNames]?.split(","),
                animagus = row[Characters.animagus],
                boggart = row[Characters.boggart],
                patronus = row[Characters.patronus],
                birth = row[Characters.birth],
                death = row[Characters.death],
                familyMembers = row[Characters.familyMembers]?.split(","),
                house = row[Characters.house],
                imageUrl = row[Characters.imageUrl],
                jobs = row[Characters.jobs]?.split(","),
                nationality = row[Characters.nationality],
                slug = row[Characters.slug],
                species = row[Characters.species],
                titles = row[Characters.titles]?.split(","),
                wands = row[Characters.wands]?.split(","),
                gender = row[Characters.gender],
                blood_status = row[Characters.bloodStatus],
            )
        }
    }

    fun getPotions(): List<Potion> = transaction {
        Potions.selectAll().map { row ->
            Potion(
                id = row[Potions.id],
                name = row[Potions.name],
                characteristics = row[Potions.characteristics]?.split(",").toString(),
                difficulty = row[Potions.difficulty],
                effect = row[Potions.effect],
                wiki = row[Potions.wiki],
                imageUrl = row[Potions.imageUrl],
                inventors = row[Potions.inventors]?.split(",").toString(),
                ingredients = row[Potions.ingredients]?.split(",").toString(),
                manufacturers = row[Potions.manufacturers]?.split(",").toString(),
                sideEffects = row[Potions.sideEffects]?.split(",").toString(),
                slug = row[Potions.slug],
                time = row[Potions.time],
            )
        }
    }

    fun getSpells(): List<Spell> = transaction {
        Spells.selectAll().map { row ->
            Spell(
                id = row[Spells.id],
                name = row[Spells.name],
                category = row[Spells.category],
                creator = row[Spells.creator],
                effect = row[Spells.effect],
                hand = row[Spells.hand],
                wiki = row[Spells.wiki],
                imageUrl = row[Spells.imageUrl],
                incantation = row[Spells.incantation],
                light = row[Spells.light],
                slug = row[Spells.slug],
            )
        }
    }
}

val daoapi = DAOApi()