package ru.jacobkant.githubapp.ui.main

import androidx.lifecycle.ViewModel
import ru.jacobkant.githubapp.data.androidUtils.ActivityResultChannel
import ru.jacobkant.githubapp.domain.AuthRepository
import ru.jacobkant.githubapp.ui.common.Screens
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    val resultChannel: ActivityResultChannel,
    val rootCicerone: Cicerone<Router>
) : ViewModel() {
    init {
        if (authRepository.isLoggedIn()) {
            rootCicerone.router.newRootScreen(Screens.MainScreen())
        } else {
            rootCicerone.router.newRootScreen(Screens.LoginScreen())
        }
    }
}