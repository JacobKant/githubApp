package ru.jacobkant.githubapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import ru.jacobkant.githubapp.domain.AuthRepository
import ru.jacobkant.githubapp.domain.MyProfileInfo
import ru.jacobkant.githubapp.ui.main.MenuFragmentContainerViewModel
import ru.jacobkant.githubapp.utils.SchedulersProviderForTest

class MenuFragmentContainerViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repo: AuthRepository = mockk(relaxUnitFun = true)
    private val stubProfile = MyProfileInfo(
        "test",
        "test",
        "test",
        "test"
    )

    @Test
    fun `profile from api success loaded`() {
        every { repo.getCurrentUser() } returns Single.just(stubProfile)

        val viewModel =
            MenuFragmentContainerViewModel(
                repo,
                SchedulersProviderForTest()
            )

        val profile = viewModel.myProfileInfo.value
        Assert.assertEquals(stubProfile, profile)
    }

    @Test
    fun `click logout - delegate to repository`() {
        every { repo.getCurrentUser() } returns Single.just(stubProfile)
        val viewModel =
            MenuFragmentContainerViewModel(
                repo,
                SchedulersProviderForTest()
            )

        viewModel.clickLogout()

        verify { repo.logOut() }
    }

}