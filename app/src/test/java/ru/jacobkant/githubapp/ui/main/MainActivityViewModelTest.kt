package ru.jacobkant.githubapp.ui.main

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import ru.jacobkant.githubapp.data.androidUtils.ActivityResultChannel
import ru.jacobkant.githubapp.domain.AuthRepository
import ru.jacobkant.githubapp.ui.common.Screens
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class MainActivityViewModelTest {
    private val authRepository: AuthRepository = mockk(relaxUnitFun = true)
    private val cicerone: Cicerone<Router> = mockk(relaxUnitFun = true)
    private val channel: ActivityResultChannel = mockk(relaxUnitFun = true)

    @Test
    fun `test start screen main`() {
        every { authRepository.isLoggedIn() } returns true
        every { cicerone.router.newRootScreen(any()) } returns Unit
        MainActivityViewModel(authRepository, channel, cicerone)
        verify { cicerone.router.newRootScreen(ofType(Screens.MainScreen::class)) }
    }

    @Test
    fun `test start screen login`() {
        every { authRepository.isLoggedIn() } returns false
        every { cicerone.router.newRootScreen(any()) } returns Unit
        MainActivityViewModel(authRepository, channel, cicerone)
        verify { cicerone.router.newRootScreen(ofType(Screens.LoginScreen::class)) }
    }
}