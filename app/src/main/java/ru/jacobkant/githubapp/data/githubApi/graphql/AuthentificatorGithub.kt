package ru.jacobkant.githubapp.data.githubApi.graphql

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import ru.jacobkant.githubapp.BuildConfig

class AuthentificatorGithub : Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        return if (response.code() == 401)
            response.request().newBuilder()
                .header("Authorization", "bearer ${BuildConfig.GITHUB_TOKEN}")
                .build()
        else null
    }

}