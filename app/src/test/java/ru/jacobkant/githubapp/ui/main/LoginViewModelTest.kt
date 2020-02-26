package ru.jacobkant.githubapp.ui.main

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.jacobkant.githubapp.data.androidUtils.ActivityResult
import ru.jacobkant.githubapp.data.androidUtils.ActivityResultChannel
import ru.jacobkant.githubapp.data.vkApi.VkApi
import ru.jacobkant.githubapp.domain.AuthRepository
import ru.jacobkant.githubapp.ui.common.Screens
import ru.jacobkant.githubapp.ui.login.LoginViewModel
import ru.terrakok.cicerone.Router

class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val authRepository: AuthRepository = mockk(relaxUnitFun = true)
    private val router: Router = mockk(relaxUnitFun = true)
    private val resultChannel: ActivityResultChannel = mockk(relaxUnitFun = true)
    private val vkApi: VkApi = mockk(relaxUnitFun = true)

    @Before
    fun setUp() {
        every { resultChannel.observable() } returns Observable.never()
    }

    @Test
    fun `click loginVk emit login side effect`() {
        val viewModel = LoginViewModel(
            authRepository,
            router,
            vkApi,
            resultChannel
        )
        viewModel.clickVkLogin()
        Assert.assertTrue(viewModel.loginVkSideEffect.value == Unit)
    }

    @Test
    fun `vk login result success - save token and navigate to main screen`() {
        val vkCallbackSlot = slot<VKAuthCallback>()
        every { vkApi.receiveVkLoginResult(any(), capture(vkCallbackSlot)) } returns Unit
        every { router.newRootScreen(any()) } returns Unit
        every { resultChannel.observable() } returns Observable.just(
            ActivityResult(
                123,
                Activity.RESULT_OK,
                mockk()
            )
        )
        val viewModel = LoginViewModel(
            authRepository,
            router,
            vkApi,
            resultChannel
        )
        vkCallbackSlot.captured.onLogin(VKAccessToken(mapOf("access_token" to "test_token")))

        verify { router.newRootScreen(ofType<Screens.MainScreen>()) }
    }
}