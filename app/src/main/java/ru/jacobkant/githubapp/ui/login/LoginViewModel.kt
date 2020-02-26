package ru.jacobkant.githubapp.ui.login

import androidx.lifecycle.ViewModel
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import io.reactivex.disposables.CompositeDisposable
import ru.jacobkant.githubapp.data.androidUtils.ActivityResult
import ru.jacobkant.githubapp.data.androidUtils.ActivityResultChannel
import ru.jacobkant.githubapp.data.androidUtils.SingleLiveData
import ru.jacobkant.githubapp.data.vkApi.VkApi
import ru.jacobkant.githubapp.domain.AuthRepository
import ru.jacobkant.githubapp.ui.common.Screens
import ru.jacobkant.githubapp.utils.addTo
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val rootRouter: Router,
    private val vkApi: VkApi,
    resultChannel: ActivityResultChannel
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val loginVkSideEffect = SingleLiveData<Unit>()

    init {
        resultChannel.observable()
            .subscribe(this::onVkLoginResult)
            .addTo(compositeDisposable)
    }

    fun clickVkLogin() {
        loginVkSideEffect.value = Unit
    }

    private fun onVkLoginResult(it: ActivityResult) {
        vkApi.receiveVkLoginResult(it, object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                authRepository.setVkToken(token.accessToken)
                rootRouter.newRootScreen(Screens.MainScreen())
            }

            override fun onLoginFailed(errorCode: Int) {
                // todo show toast?
            }
        })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
