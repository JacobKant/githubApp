package ru.testDi

import dagger.Module
import dagger.Provides
import io.mockk.mockk
import ru.jacobkant.githubapp.data.githubApi.graphql.GithubApi
import ru.jacobkant.githubapp.data.preferences.DefaultPreferences
import ru.jacobkant.githubapp.data.vkApi.VkApi
import javax.inject.Singleton

@Module
class FakeExternalApiModule {
    @Provides
    @Singleton
    fun vkApi(): VkApi {
        return mockk(relaxUnitFun = true)
    }

    @Provides
    @Singleton
    fun githubApi(): GithubApi {
        return mockk(relaxUnitFun = true)
    }

    @Provides
    @Singleton
    fun defaultPreferences(): DefaultPreferences {
        return mockk(relaxUnitFun = true)
    }
}