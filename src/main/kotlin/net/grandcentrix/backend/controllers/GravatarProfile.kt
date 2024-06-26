package net.grandcentrix.backend.controllers

import net.grandcentrix.backend.dao.daoUsers
import net.grandcentrix.backend.models.GravatarProfile
import net.grandcentrix.backend.plugins.GravatarProfileException
import net.grandcentrix.backend.plugins.api.APIRequesting.fetchGravatarProfile

class GravatarProfile {

    fun getGravatarProfile(userSession: UserSession): GravatarProfile {
        val username = userSession.username
        val userEmail = daoUsers.getItem(username)?.email
            ?: throw GravatarProfileException("User not found for gravatar")

        val gravatar = fetchGravatarProfile(userEmail)

        return gravatar
    }
}