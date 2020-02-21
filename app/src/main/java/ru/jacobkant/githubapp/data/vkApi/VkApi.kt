package ru.jacobkant.githubapp.data.vkApi

import io.reactivex.Single
import ru.jacobkant.githubapp.data.MyVkUser

interface VkApi {
    //    fun login(): Single<String>
    fun requestMyUser(): Single<MyVkUser>
}