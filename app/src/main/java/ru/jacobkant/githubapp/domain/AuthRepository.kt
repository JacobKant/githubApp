package ru.jacobkant.githubapp.domain

import io.reactivex.Single
import ru.jacobkant.githubapp.data.MyVkUser

interface AuthRepository {
    fun getCurrentUser(): Single<MyVkUser>
}