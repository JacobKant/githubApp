package ru.jacobkant.githubapp.domain

import io.reactivex.Single

interface AuthRepository {
    fun getCurrentUser(): Single<MyProfileInfo>
    fun setVkToken(token: String)
    fun isLoggedIn(): Boolean
    fun logOut()
}