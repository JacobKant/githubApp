package ru.jacobkant.githubapp.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.jacobkant.githubapp.data.vkApi.VkApi
import ru.jacobkant.githubapp.domain.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val vkApi: VkApi) :
    AuthRepository {
    private var myUser: MyVkUser? = null

    override fun getCurrentUser(): Single<MyVkUser> {
        if (myUser != null)
            return Single.just(myUser)

        return vkApi.requestMyUser()
            .doOnSuccess { myUser = it }
            .subscribeOn(Schedulers.io())
    }
}

