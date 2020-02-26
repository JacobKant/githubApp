package ru.jacobkant.githubapp.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.jacobkant.githubapp.data.preferences.DefaultPreferences
import ru.jacobkant.githubapp.data.vkApi.VkApi
import ru.jacobkant.githubapp.domain.AuthRepository
import ru.jacobkant.githubapp.domain.MyProfileInfo
import ru.jacobkant.githubapp.ui.common.Screens
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val vkApi: VkApi,
    private val defPrefs: DefaultPreferences,
    private val cicerone: Cicerone<Router>
) : AuthRepository {

    private var myUser: MyProfileInfo? = null

    override fun getCurrentUser(): Single<MyProfileInfo> {
        if (myUser != null)
            return Single.just(myUser)

        return vkApi.requestMyUser()
            .doOnSuccess { myUser = it }
            .subscribeOn(Schedulers.io())
    }

    override fun isLoggedIn(): Boolean {
        return defPrefs.vkToken.isNotEmpty()
    }

    override fun logOut() {
        defPrefs.vkToken = ""
        vkApi.logOut()
        cicerone.router.newRootScreen(Screens.LoginScreen())
    }

    override fun setVkToken(token: String) {
        defPrefs.vkToken = token
    }
}

